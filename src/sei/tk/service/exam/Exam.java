package sei.tk.service.exam;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.exam.component.ExamRecorder;
import sei.tk.service.exam.component.PaperProvider;
import sei.tk.service.exam.component.SubjectChecker;
import sei.tk.service.exam.component.TimeWatcher;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuruijie on 2016/4/7.
 * 考试实例
 */
@Service
public class Exam {
    private ExamMapForRedis examMapForRedis;//用于考试时访问redis缓存的对象

    @Resource
    private PaperProvider paperProvider;//试卷提供者，负责与试卷相关的功能
    @Resource
    private SubjectChecker subjectChecker;//题目检验者，负责与题目相关的功能
    @Resource
    private TimeWatcher timeWatcher;//时间监控者，负责监控时间相关的功能
    @Resource
    private ExamRecorder examRecorder;//考试记录者，负责记录本次考试的信息

    public Exam(){
        examMapForRedis=new ExamMapForRedis();
    }
    /**
     * 初始化考试
     * @param stuId 学生学号
     * @param testpaperId 试卷编号
     */
    public void initExam(long stuId,long testpaperId){
        if(!paperProvider.isPaperAvailable(stuId,testpaperId))//检查试卷可用否
            throw new TKException(TkConfig.INVALID_ACTION,"你现在不能进行该考试");
        //将试卷放入缓存
        examMapForRedis.put(stuId,TkConfig.TEST_PAPER_INFO, paperProvider.obtainTestPaper(testpaperId));
        //将试卷的所有题目放入缓存
        List<TkSubject> tkSubjectList=paperProvider.paperSubjects(testpaperId);
        List<SubjectInfoVo> subjectInfoVoList=new ArrayList<>();
        for(TkSubject ts:tkSubjectList){
            subjectInfoVoList.add(subjectChecker.subjectPoToVo(ts, testpaperId));
        }
        StuAnswerVo stuAnswerVo=new StuAnswerVo();
        stuAnswerVo.setStuId(stuId);
        stuAnswerVo.setAnswers(subjectInfoVoList);
        examMapForRedis.put(stuId,TkConfig.TEST_STU_ANSWER,stuAnswerVo);
        //将开始时间放入缓存
        examMapForRedis.put(stuId,TkConfig.TEST_START_TIME,timeWatcher.startTime());
    }

    /**
     * 考试是否已经建立
     * @param stuId 学生学号
     * @return 是否建立
     */
    public boolean isBuilded(long stuId){
        return examMapForRedis.get(stuId,TkConfig.TEST_PAPER_INFO)!=null
                &&examMapForRedis.get(stuId,TkConfig.TEST_START_TIME)!=null
                &&examMapForRedis.get(stuId,TkConfig.TEST_STU_ANSWER)!=null;
    }

    public void destroyExam(long stuId){
        examMapForRedis.remove(stuId);
    }
    /**
     * 记录考试信息
     * @param stuId 学生学号
     */
    public void recordExam(long stuId){
        examRecorder.recordAnswer(stuId,examMapForRedis);//记录答题情况
        examRecorder.recordScore(stuId,examMapForRedis);//记录分数
        examRecorder.recordTest(stuId,examMapForRedis);//记录考试信息
    }

    /**
     * 考试时间是否已经耗尽
     * @param stuId 学生学号
     * @return 时间是否耗尽
     */
    public boolean isTimeOut(long stuId){
        return getOddTime(stuId)<=0;
    }

    /**
     * 获取剩余时间
     * @param stuId 学生学号
     * @return 剩余时间
     */
    public long getOddTime(long stuId){
        //从缓存中拿卷子
        TestpaperInfVo testpaperInfVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_PAPER_INFO),TestpaperInfVo.class);
        //从缓存中拿考试开始时间
        long startTime=Long.parseLong(examMapForRedis.get(stuId,TkConfig.TEST_START_TIME));
        //计算剩余时间并判断
        return timeWatcher.oddTime(System.currentTimeMillis(),testpaperInfVo.getTotalTime().longValue()*60*1000,startTime);
    }

    public void setTempAnswer(long stuId,SubjectInfoVo[] subjectInfoVos){
        StuAnswerVo stuAnswerVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_STU_ANSWER),StuAnswerVo.class);
        if(subjectInfoVos==null)return;
        for(int i=0;i<subjectInfoVos.length;i++) {
            SubjectInfoVo subjectInfoVo = subjectInfoVos[i];
            String[] stuBlankAnswer = subjectInfoVo.getStuBlankAnswer();
            subjectChecker.TempAnswer(stuAnswerVo,subjectInfoVo.getNumber(),subjectInfoVo.getStuAnswer(),stuBlankAnswer);
        }
        examMapForRedis.put(stuId,TkConfig.TEST_STU_ANSWER,stuAnswerVo);
    }

    public PaperProvider getPaperProvider() {
        return paperProvider;
    }

    public void setPaperProvider(PaperProvider paperProvider) {
        this.paperProvider = paperProvider;
    }

    public SubjectChecker getSubjectChecker() {
        return subjectChecker;
    }

    public void setSubjectChecker(SubjectChecker subjectChecker) {
        this.subjectChecker = subjectChecker;
    }

    public TimeWatcher getTimeWatcher() {
        return timeWatcher;
    }

    public void setTimeWatcher(TimeWatcher timeWatcher) {
        this.timeWatcher = timeWatcher;
    }

    public ExamRecorder getExamRecorder() {
        return examRecorder;
    }

    public void setExamRecorder(ExamRecorder examRecorder) {
        this.examRecorder = examRecorder;
    }

    public void setExamMapForRedis(ExamMapForRedis examMapForRedis) {
        this.examMapForRedis = examMapForRedis;
    }

    public ExamMapForRedis getExamMapForRedis(){
        return examMapForRedis;
    }
}
