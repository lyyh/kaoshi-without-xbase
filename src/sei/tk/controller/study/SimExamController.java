package sei.tk.controller.study;

/**
 * 模拟考试Controller
 * Created by liuyanhao on 2016/3/26 0026.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.study.SimExamService;
import sei.tk.service.test.AnswerService;
import sei.tk.service.test.ExamService;
import sei.tk.service.test.TimeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller(value = "/sim")
public class SimExamController {
    @Resource
    SimExamService simExamService;
    @Resource
    private ExamService examService;
    @Resource
    private AnswerService answerService;
    @Resource
    private TimeService timeService;

    @RequestMapping("/getSubject")
    @ResponseBody
    public SubjectInfoVo getSubject(HttpSession session,HttpServletRequest request,Integer nextNumber,Integer thisNumber,String[] blankAnswers,String stuAnswer){
        //暂存本题学生答案
        if(stuAnswer!=null)answerService.setTempAnswer(session,thisNumber-1,stuAnswer,blankAnswers);
        //获取需要 得到的下一题目信息（在service中判断是否从stuAnswerVo中拿）
        SubjectInfoVo subjectInfoVo=examService.getSubjet(session, nextNumber-1);
        subjectInfoVo.setNumber(nextNumber);
        return subjectInfoVo;
    }

    @RequestMapping("/getTime")
    @ResponseBody
    public Long getOddTime(HttpSession session,HttpServletRequest request){
        //获取时间
        return timeService.getOddTime(session);
    }

    @RequestMapping("/initExam")
    @ResponseBody
    public TestpaperInfVo initExam(HttpSession session,HttpServletRequest request,Long stuId){
        //判断考试是否已经初始化过
        TestpaperInfVo testpaperInfVo=(TestpaperInfVo)session.getAttribute("testpaperInfVo");
//        Long startTime= (Long) session.getAttribute("startTime");
        if (testpaperInfVo!=null)return testpaperInfVo;
        //if(startTime!=null)return null;
        //通过stuId获得模拟考试的testpaperId
        Long testpaperId = simExamService.getTestpaperId(stuId);
        //初始化考试
        testpaperInfVo=examService.initExam(session,testpaperId); //拿卷
        timeService.setStart(session);//记录开始时间
        return testpaperInfVo;
    }

    /**
     * 提交试卷
     * @param session
     * @param request
     * @param thisNumber
     * @param stuAnswer
     * @param blankAnswers
     * @return
     */
    @RequestMapping("submitPaper")
    @ResponseBody
    public Integer submitPaper(HttpSession session,HttpServletRequest request,Integer thisNumber,String stuAnswer,String[] blankAnswers){//提交答卷，记录考试信息
        if(stuAnswer!=null)answerService.setTempAnswer(request.getSession(),thisNumber-1,stuAnswer,blankAnswers);
        Integer score = answerService.submitTestPaper(request);
        examService.destroyExam(session);//摧毁试卷
        return score;
    }

    /**
     * 初始化模拟考试的试卷
     * @param session
     * @return
     */
    @RequestMapping(value = "/initPaper")
    @ResponseBody
    public Long getSimPaperId(HttpSession session){
        Long ppassportId = (Long) session.getAttribute("sessionPassport");
        return ppassportId;
    }
}
