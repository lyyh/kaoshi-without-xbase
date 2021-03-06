package sei.tk.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.study.DoSubjectService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ywl 2016/5/24.
 * 刷题相关
 */
@Controller
@RequestMapping("/study")
public class DoSubjectController extends TkBaseController{
    @Resource
    DoSubjectService doSubjectService;
    @RequestMapping("/getnums")
    @ResponseBody
    public int getnums(Short courseId,Long knopointId,Short quetypeId){
            return doSubjectService.getSubjectnums(courseId,knopointId,quetypeId);
        }
    @RequestMapping("/getsubjects")
    @ResponseBody
    public Object getSubjects(Short courseId,Long knopointId,Short quetypeId,Integer page, Integer rows){
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "",doSubjectService.getSubjects(courseId,knopointId,quetypeId,page,rows));
    }
    @RequestMapping("/getsubjectandanswer")
    @ResponseBody
    public Object getSubjectsAndAnswer(Short courseId,Long knopointId,Short quetypeId,Integer page, Integer rows){
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "",doSubjectService.getSubjectsAndAnswer(courseId, knopointId, quetypeId, page, rows));
    }
    @RequestMapping("/collectsubjects")
    @ResponseBody
    public Object collectSubjects(HttpSession session,@RequestBody TkSubject[] tkSubjects){
        doSubjectService.collectSub(Arrays.asList(tkSubjects),session);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,null);
    }



}
