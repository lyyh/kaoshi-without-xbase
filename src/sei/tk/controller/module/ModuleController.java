package sei.tk.controller.module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.module.ModuleService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/5/24.
 */
@Controller
@RequestMapping("api/modules")
public class ModuleController extends TkBaseController{
    @Resource
    ModuleService moduleService;

    @RequestMapping(value = "menuList",method = RequestMethod.GET)
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_STUDENT,TkConfig.ROLE_TEACHER,TkConfig.ROLE_ADMIN})
    public Object menuList(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        return LittleUtil.constructResponse(TkConfig.SUCCESS, null, moduleService.menuList(sessionPassport));
    }
}
