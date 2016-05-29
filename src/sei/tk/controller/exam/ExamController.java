package sei.tk.controller.exam;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.exam.Exam;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/4/8.
 */
@Controller
@RequestMapping("exam")
public class ExamController extends TkBaseController{
    @Resource
    Exam exam;

    @RequestMapping("initExam")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object initExam(HttpSession session,HttpServletRequest request,Long scheduleId,Long testpapaerId){
        testpapaerId=3L;
        scheduleId=2L;
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        if(!exam.isBuilded(sessionPassport.getPassportId())) {//若考试未建立，则建立
            exam.initExam(sessionPassport.getPassportId(), testpapaerId);
            exam.getExamMapForRedis().put(sessionPassport.getPassportId(), "scheduleId", scheduleId);
            exam.getExamMapForRedis().put(sessionPassport.getPassportId(), "ip", request.getRemoteAddr());
        }
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "", null);
    }

    @RequestMapping("getOddTime")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object testPanel(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        long time=exam.getOddTime(sessionPassport.getPassportId());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",""+time);
    }

    @RequestMapping("cacheAnswer")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object cacheAnswer(HttpSession session,@RequestBody SubjectInfoVo[] subjectInfoVos){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        exam.setTempAnswer(sessionPassport.getPassportId(),subjectInfoVos);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",null);
    }

    @RequestMapping("submitPaper")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object submitPaper(HttpSession session,@RequestBody SubjectInfoVo[] subjectInfoVos){
        SessionPassport sessionPassport=(SessionPassport) session.getAttribute("sessionPassport");
        Integer score;
        exam.setTempAnswer(sessionPassport.getPassportId(), subjectInfoVos);//记录答案
        StuAnswerVo stuAnswerVo = JSON.parseObject(exam.getExamMapForRedis()
                .get(sessionPassport.getPassportId(), "stuAnswer"), StuAnswerVo.class);//获取学生的答案
        score = exam.getSubjectChecker().checkSubjects(stuAnswerVo);//计算选择判断题分数
        exam.getExamMapForRedis().put(sessionPassport.getPassportId(),"score",score);
        exam.recordExam(sessionPassport.getPassportId());//记录考试信息
        exam.destroyExam(sessionPassport.getPassportId());//释放考试所占用的资源
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"试卷提交成功,选择判断题得分："+score+"分," +
                "请在成绩公布后上教务在线查询最终成绩",score);
    }

    @RequestMapping("examInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object getTestInfo(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        String json=exam.getExamMapForRedis().get(sessionPassport.getPassportId(),TkConfig.TEST_PAPER_INFO);
        System.out.println(json);
        TestpaperInfVo testpaperInfVo=JSON.parseObject(json,TestpaperInfVo.class);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",testpaperInfVo);
    }

    @RequestMapping("questionInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object getQuestionInfo(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        String json=exam.getExamMapForRedis().get(sessionPassport.getPassportId(),TkConfig.TEST_STU_ANSWER);
//        System.out.println(json);
        StuAnswerVo stuAnswerVo= JSON.parseObject(json, StuAnswerVo.class);
        System.out.println(JSON.toJSONString(stuAnswerVo.getAnswers()));
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",stuAnswerVo.getAnswers());
    }

    @RequestMapping("questionsPanel")
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object questionPanel(HttpSession session,Model model){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        String json=exam.getExamMapForRedis().get(sessionPassport.getPassportId(),TkConfig.TEST_STU_ANSWER);
        StuAnswerVo stuAnswerVo= JSON.parseObject(json,StuAnswerVo.class);
        model.addAttribute("subjects",stuAnswerVo.getAnswers());
        return "tiku/page/student/test-questions";
    }

    @RequestMapping("testPanel")
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object testPanel(HttpSession session,Model model){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        String json=exam.getExamMapForRedis().get(sessionPassport.getPassportId(),"testpaperInfo");
        TestpaperInfVo testpaperInfVo=JSON.parseObject(json,TestpaperInfVo.class);
        model.addAttribute("paper",testpaperInfVo);
        return "tiku/page/student/student-test-dialog";
    }
}
