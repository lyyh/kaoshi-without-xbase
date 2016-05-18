package sei.tk.service.test.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.test.AnswerService;
import sei.tk.service.test.TimeService;
import sei.tk.util.StudyUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by liuruijie on 2016/3/12.
 * 处理学生提交答案的业务
 */
@Service
public class AnswerImpl implements AnswerService{
    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkTestanswerMapper tkTestanswerMapper;
    @Resource
    TkTestMapper tkTestMapper;
    @Resource
    TimeService timeService;
    @Resource
    TkTestscheduleMapper tkTestscheduleMapper;

    @Override
    public void setTempAnswer(HttpSession session, Integer number, String stuAnswer,String[] stuBlankAnswer) { //暂存学生答案（包括题目具体内容）
        //获取题目编号列表
        List<TkTestsubject> tkTestsubjectList= (List<TkTestsubject>) session.getAttribute("tkTestsubjectList");
        if(tkTestsubjectList==null)return;
        //获取本题id
        TkTestsubject tkTestsubject=tkTestsubjectList.get(number);
        //根据id查询题的相关信息
        TkSubjectWithBLOBs tkSubjectWithBLOBs=tkSubjectMapper.selectByPrimaryKey(tkTestsubject.getSubjectId());
        //设置题目信息对象
        SubjectInfoVo subjectInfoVo=new SubjectInfoVo();
        subjectInfoVo.setScore(new Integer(tkTestsubject.getTestpaperScore()));
        subjectInfoVo.setSubjectId(tkTestsubject.getSubjectId());
        subjectInfoVo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
        subjectInfoVo.setType(new Integer(tkSubjectWithBLOBs.getQuetypeId()));
        //选择题拆分选项
        if(subjectInfoVo.getType()==1) {
            String[] options = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
            subjectInfoVo.setSubjectOptions(Arrays.asList(options));
            subjectInfoVo.setStuAnswer(stuAnswer);
            //填空题
        }else if(subjectInfoVo.getType()==3){
            String[] blanks=tkSubjectWithBLOBs.getSubjectAnswer().split("@#%");
            subjectInfoVo.setStuBlankAnswer(stuBlankAnswer);
            subjectInfoVo.setBlankamount(blanks.length);
            subjectInfoVo.setStuAnswer(StudyUtil.linkBlank(stuBlankAnswer));
        }else {
            subjectInfoVo.setStuAnswer(stuAnswer);
        }

        //从session中读取暂存的学生答案对象，如果没有则创建
        StuAnswerVo stuAnswerVo= (StuAnswerVo) session.getAttribute("stuAnswerVo");
        Map<Long,SubjectInfoVo> answers=null;
        if(stuAnswerVo!=null){
//            answers=stuAnswerVo.getAnswers();
        }else {
            stuAnswerVo = new StuAnswerVo();
            answers=new HashMap<Long, SubjectInfoVo>();
//            stuAnswerVo.setAnswers(answers);
        }
        //保存答案
        answers.put(subjectInfoVo.getSubjectId(),subjectInfoVo);
        //暂存session
        session.setAttribute("stuAnswerVo",stuAnswerVo);
        //session.setAttribute(""+subjectId,subjectInfoVo);
    }

    @Override
    public Integer submitTestPaper(HttpServletRequest request) {//最终提交试卷
        HttpSession session=request.getSession();
        //获取一些暂存session的信息
        StuAnswerVo stuAnswerVo= (StuAnswerVo) session.getAttribute("stuAnswerVo");
        TestpaperInfVo testpaperInfVo= (TestpaperInfVo) session.getAttribute("testpaperInfVo");

        TkTestsubject tkTestsubject=null;
        //答卷校验
        Long oddTime=null;
        try {
            oddTime=timeService.getOddTime(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(stuAnswerVo==null){
            stuAnswerVo=new StuAnswerVo();
//            stuAnswerVo.setAnswers(new HashMap<Long, SubjectInfoVo>());
        }
        stuAnswerVo.setAnswersAmount(stuAnswerVo.getAnswers().size());
//        if(!stuAnswerVo.isFinished()) return -1;
//        Map<Long,SubjectInfoVo> answers=stuAnswerVo.getAnswers();
//        Set<Long> keySet=answers.keySet(); //即题号
        Integer score=0;
//        for(Long key:keySet){
//            //学生每道题的答案
//            String answer=answers.get(key).getStuAnswer();
//            //查询题目信息
//            TkSubject tkSubject=tkSubjectMapper.selectByPrimaryKey(key);
//            //查询题目分数信息
//            TkTestsubjectExample tkTestsubjectExample=new TkTestsubjectExample();
//            TkTestsubjectExample.Criteria tsCriteria=tkTestsubjectExample.createCriteria();
//            tsCriteria.andSubjectIdEqualTo(tkSubject.getSubjectId());
//            tsCriteria.andTestpaperIdEqualTo(testpaperInfVo.getTestpaperId());
//
//            List<TkTestsubject> tkTestsubjectList = tkTestsubjectMapper.selectByExample(tkTestsubjectExample);
//            tkTestsubject=tkTestsubjectList.get(0); //其中只会有一条数据
//
//            //选择、填空、判断题目正误，计算分数
//            if(tkSubject.getQuetypeId()==1||tkSubject.getQuetypeId()==2||tkSubject.getQuetypeId()==3){
//                if(answer.trim().equals(tkSubject.getSubjectAnswer())){
//                    score+=tkTestsubject.getTestpaperScore();
//                }
//            }
//
//            //将答卷存到数据库
//            TkTestanswer tkTestanswer=new TkTestanswer();
//            tkTestanswer.setSubjectId(tkSubject.getSubjectId());
//            tkTestanswer.setTestpaperId(testpaperInfVo.getTestpaperId());
//            tkTestanswer.setPassportId(1L);
//            tkTestanswer.setSubjectAnswer(answer);
//            tkTestanswerMapper.insertSelective(tkTestanswer);
//        }
        //记录本次考试信息
        TkTest tkTest=new TkTest();
        tkTest.setPassportId(1L);
        tkTest.setTestpaperId(testpaperInfVo.getTestpaperId());
        tkTest.setTestTime(new Date());
        tkTest.setTestIp(request.getRemoteAddr());
        tkTestMapper.insertSelective(tkTest);

        //记录分数
        TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(1L);
        criteria.andTestpaperIdEqualTo(testpaperInfVo.getTestpaperId());

        TkTestschedule tkTestschedule=new TkTestschedule();
        tkTestschedule.setStuBasescore(score.shortValue());
        tkTestschedule.setStuScore(tkTestschedule.getStuBasescore());
        tkTestscheduleMapper.updateByExampleSelective(tkTestschedule,tkTestscheduleExample);

        return score;
    }
}
