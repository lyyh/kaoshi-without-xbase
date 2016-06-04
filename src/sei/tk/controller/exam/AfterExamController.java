package sei.tk.controller.exam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.exam.AfterExamService;
import sei.tk.service.exam.vo.GenExam;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/6/2.
 */

@Controller
@RequestMapping("afterExam")
public class AfterExamController extends TkBaseController{

    @Resource
    AfterExamService afterExamService;

    @RequestMapping("myExam")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object myExam(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,afterExamService.listMyExam(sessionPassport.getPassportId()));
    }

    @RequestMapping("grads/analyses")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object listGrads(HttpSession session, GenExam genExam,Integer currentPage, Integer rows){
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,afterExamService.listAllGradsByPage(currentPage, rows, genExam));
    }

    @RequestMapping("grads/analyses/details")
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object showGrads(HttpSession session, Model model, Long testpaperId, Long passportId){
        model.addAttribute("analyse",afterExamService.getAnalyse(testpaperId, passportId));
        return "/tiku/page/teacher/teacher-analysis";
    }
}
