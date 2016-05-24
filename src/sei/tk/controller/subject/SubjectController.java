package sei.tk.controller.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.mapper.TkKnopointMapper;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.subject.SubjectInfo;
import sei.tk.service.subject.SubjectServie;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016-03-18.
 */
@Controller
@RequestMapping("/subject")
public class SubjectController extends TkBaseController{
    @Resource
    private SubjectServie subjectService;
    @Resource
    TkKnopointMapper tkKnopointMapper;

    @RequestMapping("/add")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object addSubject(HttpSession session,@RequestBody SubjectInfo subjectInfo) {   //提交所出的题目
//        removeP(subjectInfo);
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionTeacher");
        subjectInfo.setPassportId(sessionPassport.getPassportId());
        subjectService.addSubject(subjectInfo);
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "添加题目成功", null);
    }

    @RequestMapping("/list")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object listSubject(HttpSession session,SubjectInfo tkSubject,Integer page,Integer rows){
        if(tkSubject.getType()!=null&&tkSubject.getType()==-1)tkSubject.setType(null);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",subjectService.getSubjects(tkSubject, page, rows));
    }

    @RequestMapping("/update")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object updateSubject(HttpSession session,@RequestBody SubjectInfo tkSubject){
//        removeP(tkSubject);
        subjectService.updateSubject(tkSubject);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"更新题目成功",null);
    }

    @RequestMapping("/show")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object showSubject(HttpSession session,Long subjectId){
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",subjectService.getSubjectInfoById(subjectId));
    }

    @RequestMapping("/delete")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object deleteSubjectBatch(HttpSession session,@RequestParam("subjectId[]") Long[] subjectIds){
        subjectService.deleteSubjectBatch(subjectIds);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"删除成功，共删除"+subjectIds.length+"条记录",null);
    }

    @RequestMapping("/detailsPage")
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object detailsPage(HttpSession session,Long subjectId,Model model){
        model.addAttribute("subject", subjectService.getSubjectInfoById(subjectId));
        return "/tiku/page/teacher/subject-details";
    }

    @RequestMapping("/editPage")
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object editPage(HttpSession session,Long subjectId,Model model){
        model.addAttribute("subject",subjectService.getSubjectInfoById(subjectId));
        return "/tiku/page/teacher/recordQuestionEdit";
    }


    private void removeP(SubjectInfo subjectInfo){//清楚ueditor编辑后自动生成的p标签
        subjectInfo.setSubjectSolution(LittleUtil.removeOuterTagP(subjectInfo.getSubjectSolution()));
        subjectInfo.setSubjectName(LittleUtil.removeOuterTagP(subjectInfo.getSubjectName()));
        if(subjectInfo.getType()==1||subjectInfo.getType()==5){
            String[] subjectOptions = subjectInfo.getSubjectOptions();
            for(int i=0;i<subjectOptions.length;i++){
                subjectOptions[i]=LittleUtil.removeOuterTagP(subjectOptions[i]);
            }
            subjectInfo.setSubjectOptions(subjectOptions);
        }else if(subjectInfo.getType()==3){
            String[] blankAnswer = subjectInfo.getBlankAnswer();
            for(int i=0;i<blankAnswer.length;i++){
                blankAnswer[i]=LittleUtil.removeOuterTagP(blankAnswer[i]);
            }
            subjectInfo.setBlankAnswer(blankAnswer);
        }
    }
}
