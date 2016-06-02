package sei.tk.controller.exam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.exam.AfterExamService;
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
public class AfterExamController {

    @Resource
    AfterExamService afterExamService;

    @RequestMapping("myExam")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object myExam(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,afterExamService.listMyExam(sessionPassport.getPassportId()));
    }
}
