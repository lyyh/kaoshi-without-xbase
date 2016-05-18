package sei.tk.service.exam.component.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkTestMapper;
import sei.tk.service.dao.mapper.TkTestanswerMapper;
import sei.tk.service.dao.mapper.TkTestscheduleMapper;
import sei.tk.service.dao.model.TkTest;
import sei.tk.service.dao.model.TkTestanswer;
import sei.tk.service.dao.model.TkTestschedule;
import sei.tk.service.exam.ExamMapForRedis;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.exam.component.ExamRecorder;
import sei.tk.util.StudyUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuruijie on 2016/4/8.
 * 期末考试信息记录者实现
 */
@Service
public class FormalExamRecorder implements ExamRecorder{
    @Resource
    TkTestMapper tkTestMapper;
    @Resource
    TkTestanswerMapper tkTestanswerMapper;
    @Resource
    TkTestscheduleMapper tkTestscheduleMapper;

    /**
     * 记录分数
     * @param stuId 学生学号
     * @param examMapForRedis 用于访问缓存
     */
    @Override
    public void recordScore(long stuId,ExamMapForRedis examMapForRedis) {
        int score=Integer.parseInt(examMapForRedis.get(stuId,TkConfig.TEST_SCORE));
        long testscheduleId=Long.parseLong(examMapForRedis.get(stuId,TkConfig.TEST_SCHEDULE_ID));

        TkTestschedule tkTestschedule=new TkTestschedule();
        tkTestschedule.setTestscheduleId(testscheduleId);
        tkTestschedule.setStuScore((short) score);
        tkTestschedule.setStuBasescore((short) score);
        if(0==tkTestscheduleMapper.updateByPrimaryKeySelective(tkTestschedule)){
            throw new TKException(TkConfig.DATABASE_ERROR,"考试分数记录失败");
        }
    }

    /**
     * 记录考试信息，如ip等信息
     * @param stuId 学生学号
     * @param examMapForRedis 用于访问缓存
     */
    @Override
    public void recordTest(long stuId,ExamMapForRedis examMapForRedis) {
        //从缓存中拿ip
        String ip=examMapForRedis.get(stuId,TkConfig.TEST_IP);
        TkTest tkTest=new TkTest();
        //从缓存中拿试卷与学生答案
        TestpaperInfVo testpaperInfVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_PAPER_INFO), TestpaperInfVo.class);
        StuAnswerVo stuAnswerVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_STU_ANSWER),StuAnswerVo.class);

        tkTest.setTestpaperId(testpaperInfVo.getTestpaperId());
        tkTest.setPassportId(stuAnswerVo.getStuId());
        tkTest.setTestIp(ip);
        if(tkTestMapper.insertSelective(tkTest)==0){
            throw new TKException(TkConfig.DATABASE_ERROR,"考试信息记录失败");
        }
    }

    /**
     * 记录学生答题情况
     * @param stuId 学生学号
     * @param examMapForRedis 用于访问缓存
     */
    @Override
    public void recordAnswer(long stuId,ExamMapForRedis examMapForRedis) {
        List<TkTestanswer> tkTestanswerList=new ArrayList<>();
        //从缓存中拿学生答题情况
        StuAnswerVo stuAnswerVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_STU_ANSWER),StuAnswerVo.class);
        TestpaperInfVo testpaperInfVo=JSON.parseObject(examMapForRedis.get(stuId,TkConfig.TEST_PAPER_INFO), TestpaperInfVo.class);
        List<SubjectInfoVo> answers = stuAnswerVo.getAnswers();
        //为记录列表加记录
        for(SubjectInfoVo answer:answers){
            TkTestanswer tkTestanswer=new TkTestanswer();
            tkTestanswer.setPassportId(stuAnswerVo.getStuId());
            tkTestanswer.setTestpaperId(testpaperInfVo.getTestpaperId());
            tkTestanswer.setSubjectId(answer.getSubjectId());
            if(answer.getStuAnswer()==null)answer.setStuAnswer("");
            if(answer.getStuBlankAnswer()==null){
                String[] blankAnswer={""};
                answer.setStuBlankAnswer(blankAnswer);
            }
            if(answer.getType()==3){
                answer.setStuAnswer(StudyUtil.linkBlank(
                        answer.getStuBlankAnswer()));
            }
            tkTestanswer.setSubjectAnswer(answer.getStuAnswer());
            tkTestanswerList.add(tkTestanswer);
        }
        //批量插入记录
        if(0==tkTestanswerMapper.insertBatchSelective(tkTestanswerList)){
            throw new TKException(TkConfig.DATABASE_ERROR,"答卷信息记录失败");
        }
    }
}
