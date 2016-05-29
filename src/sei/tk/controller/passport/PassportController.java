package sei.tk.controller.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.passport.LoginService;
import sei.tk.service.passport.PassportService;
import sei.tk.service.passport.vo.LoginForm;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/5/16.
 */
@Controller
@RequestMapping("api/passport")
public class PassportController extends TkBaseController {
    @Resource
    LoginService loginService;
    @Resource
    PassportService passportService;

    @RequestMapping(value = "authentication",method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(HttpSession session,HttpServletRequest request,String action,LoginForm loginForm){
        if("in".equals(action)) {
            loginForm.setIp(request.getRemoteAddr());
            SessionPassport sessionPassport = loginService.doLogin(loginForm);
            session.setAttribute("sessionPassport", sessionPassport);
        }else if("out".equals(action)){
            SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
            loginService.doLogout(sessionPassport.getPassportId());
            session.removeAttribute("sessionPassport");
        }
        return LittleUtil.constructResponse(TkConfig.SUCCESS, null, null);
    }

    @RequestMapping(value = "changePswService",method = RequestMethod.POST)
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_STUDENT,TkConfig.ROLE_TEACHER,TkConfig.ROLE_ADMIN})
    public Object changePsw(HttpSession session,String oldPsw,String newPsw){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        passportService.changePsw(sessionPassport.getPassportId(),oldPsw,newPsw);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,null);
    }

    @RequestMapping(value = "selfPassportInfo",method = RequestMethod.GET)
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_STUDENT,TkConfig.ROLE_TEACHER,TkConfig.ROLE_ADMIN})
    public Object selfInfo(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,sessionPassport);
    }
}
