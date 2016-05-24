package sei.tk.controller.subject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.subject.SubjectReference;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016-03-18.
 */
@Controller
@RequestMapping("/subjectReference")
public class SubjectReferenceController extends TkBaseController{
    @Resource
    private SubjectReference subjectReference;

    @RequestMapping("/getAllCourse")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object getAllCourse(HttpSession session) {//得到所有课程
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "", subjectReference.getAllCourse());
    }

    @RequestMapping("/getKnopoint")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object getKnopoint(HttpSession session,Short courseId,Byte chapterId) { //根据课程号得到所有知识点
        if(chapterId==null)chapterId=0;
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "", subjectReference.getKnopoint(courseId, chapterId));
    }

    @RequestMapping("/getAllType")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object getAllType(HttpSession session) {//得到所有题型
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "", subjectReference.getAllType());
    }

    @RequestMapping("/getChapterId")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object getChapterId(HttpSession session,Short courseId) {  //得到所有章节
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "", subjectReference.getChapterId(courseId));
    }

    @RequestMapping("/addCourse")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object addCourse(HttpSession session,TkCourse tkCourse) {//添加课程
        subjectReference.addCourse(tkCourse);
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "课程添加成功", null);
    }

    @RequestMapping("addKnopoint")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object addKnopoint(HttpSession session,TkKnopoint tkKnopoint) {//添加知识点
        subjectReference.addKnopoint(tkKnopoint);
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "知识点添加成功", null);
    }
}
