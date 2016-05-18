package sei.tk.controller.testSchedule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.vo.passport.SessionPassport;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;
import sei.tk.service.testSchedule.ExamInfoService;
import sei.tk.service.testSchedule.TeaSchService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 风中男子 on 2016-04-22.
 * 老师增加、删除、修改安排考试
 */
@Controller
@RequestMapping("/TeaSch")
public class TeaSchController extends TkBaseController {
    @Resource
    private TeaSchService teaSchService;

    @ResponseBody
    @RequestMapping("/addExamSch")
    public Integer addExamSch(HttpSession session, @RequestBody TestInfo testInfo) { //老师添加考试安排（暂给所有学生发送安排）
        Long sessionPassport = null;
        if ((session.getAttribute("sessionStudent")) == null) {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionTeacher")).getPpassportId();
        }else {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionStudent")).getPpassportId();
        }
        return teaSchService.addExamSch(sessionPassport,testInfo);
    }

    @ResponseBody
    @RequestMapping("/delExamSch")
    public Integer delExamSch(HttpSession session, Long testscheduleId) {  //老师删除考试安排（根据安排编号对应的安排时间）
        Long sessionPassport = null;
        if ((session.getAttribute("sessionStudent")) == null) {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionTeacher")).getPpassportId();
        }else {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionStudent")).getPpassportId();
        }
        return teaSchService.delExamSch(sessionPassport,testscheduleId);
    }

    @ResponseBody
    @RequestMapping("/editExamSch")
    public Integer editExamSch(HttpSession session,@RequestBody  TestInfo testInfo) {  //老师修改考试安排（根据安排编号对应的安排时间）
        Long sessionPassport = null;
        if ((session.getAttribute("sessionStudent")) == null) {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionTeacher")).getPpassportId();
        }else {
            sessionPassport = ((SessionPassport) session.getAttribute("sessionStudent")).getPpassportId();
        }
        return teaSchService.editExamSch(sessionPassport, testInfo);
    }
}
