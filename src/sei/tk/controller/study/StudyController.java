package sei.tk.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import sei.tk.service.robot.RobotService;
import sei.tk.service.study.ChapterStudyService;
import sei.tk.service.study.SeekStudyService;
import sei.tk.service.study.StudyAnswerService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习系统Controller
 * Created by Administrator on 2016/3/18 0018.
 */
@RequestMapping("/study")
@Controller
public class StudyController {

    @Resource
    SeekStudyService seekStudyService;
    @Resource
    StudyAnswerService studyAnswerService;
    @Resource
    ChapterStudyService chapterStudyService;
    @Resource
    RobotService robotService;
    /**
     * 返回所有课程
     * @return
     */
    @RequestMapping(value = "/showCourse")
    @ResponseBody
    public List<TkCourse> studyCourse(){
        return seekStudyService.seekCourseId();
    }

    /**
     * 根据课程返回所有章节
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/showChapter")
    @ResponseBody
    public List<Byte> studySubject(Short courseId){
        return seekStudyService.seekChapter(courseId);
    }

    /**
     * 比较单个题目的答案
     * @param answer
     * @param subjectId
     * @return
     */
//    @RequestMapping(value = "/compareAnswer")
//    @ResponseBody
//    public TkSubject compareAnswer(String answer,Long subjectId){
//        return studyAnswerService.compareAnswer(answer,subjectId);
//    }

    /**
     * 根据信息初始化章节练习（返回所有题目）
     * @param courseId
     * @param chapterId
     * @return
     */
    @RequestMapping(value = "/init")
    @ResponseBody
    public Map<String,Object> initSubjects(Short courseId,Byte chapterId){
        //根据课程id，章节id筛选出符合条件的subject
        List<TkSubject> tkSubjects = chapterStudyService.initSubject(courseId,chapterId);
        //得到将排序后的章节题目
        List<SubjectInfoVo> subjectInfoVos = chapterStudyService.sortSubjet(tkSubjects);
        //将所有题目和题目数量放到map中并返回给前台
        Map<String,Object> subMap = new HashMap<>();
        subMap.put("subInfoVos",subjectInfoVos);
        subMap.put("subNum",subjectInfoVos.size());
        return subMap;
    }

    /**
     * 根据信息初始化章节练习（返回第一道题）
     * @param request
     * @param courseId
     * @param chapterId
     * @param selectNum
     * @param blankNum
     * @param checkNum
     * @param explainNum
     * @return
     */
//    @RequestMapping(value = "/init")
//    @ResponseBody
//    public Map<String,Object> initSubjects(HttpSession session,HttpServletRequest request,Short courseId,Byte chapterId,
//                                      Integer selectNum, Integer blankNum, Integer checkNum, Integer explainNum){
//        //每次初始化章节题目的时候注销上一章节保存的session,减少服务器压力
//        session.invalidate();
//        //生成本章session
//        HttpSession session1 = request.getSession();
//        //将题目信息放到map中
//        Map<String,Object> map = new HashMap<>();
//        map.put("courseId",courseId);
//        map.put("chapterId",chapterId);
//        //将map存入session中
//        session1.setAttribute("subjectInfo",map);
//        //筛选出符合条件的subject
//        List<TkSubject> tkSubjects = chapterStudyService.initSubject(session1,courseId,chapterId,selectNum,blankNum,
//                checkNum,
//                explainNum);
//        //将所有subjectInfoVo存入session中
//        session1.setAttribute("subjectInfoVos",chapterStudyService.sortSubjet(tkSubjects));
//        //从session中获得所有subjectInfoVo
//        List<SubjectInfoVo> subjectInfoVos = (List<SubjectInfoVo>) session1.getAttribute("subjectInfoVos");
//        //得到第一道题
//        SubjectInfoVo subjectInfoVo = chapterStudyService.getSubject(subjectInfoVos,1);
//        //返回第一道题和题目数量
//        Map<String,Object> subMap = new HashMap<>();
//        subMap.put("subInfoVos",subjectInfoVo);
//        subMap.put("subNum",subjectInfoVos.size());
//        return subMap;
//    }

    /**
     * 返回每一题的结果(返回正确答案和解析）
     * @param stuAnswer
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Map<String,String> checkSubject(String stuAnswer,Long subjectId){
        return chapterStudyService.subjecRes(stuAnswer,subjectId);
    }

    /**
     * 跳转到某一道题
     * @param request
     * @param subjectNum
     * @return
     */
    @RequestMapping(value = "/skip")
    @ResponseBody
    public SubjectInfoVo skipSubject(HttpServletRequest request,int subjectNum){
                HttpSession session = request.getSession();
        //得到seesion中存储的题目
        List<SubjectInfoVo> subjectInfoVos = (List<SubjectInfoVo>) session.getAttribute("subjectInfoVos");
        SubjectInfoVo subjectInfoVo = chapterStudyService.getSubject(subjectInfoVos,subjectNum);
        return subjectInfoVo;
    }

    /**
     * 得到题目数量
     * @param subjectInfoVos
     * @return
     */
    @RequestMapping(value = "/subNum")
    @ResponseBody
    public int subNum(List<SubjectInfoVo> subjectInfoVos){
        return chapterStudyService.getSubNum(subjectInfoVos);
    }



    /**
     * 用于测试
     * @return
     */
    @RequestMapping(value="/test")
    @ResponseBody
    public List<Byte> test(@RequestParam(value = "array[]",required = false)String[] array){
//        System.out.println(a);
        System.out.println(array);
        return null;
//        List<Byte> list = new ArrayList<>();
//        Byte b = 1;
//        list.add(b);
//        b = 2;
//        list.add(b);
//        b = 3;
//        list.add(b);
//        return list;
    }


}
