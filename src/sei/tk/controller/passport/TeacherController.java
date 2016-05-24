package sei.tk.controller.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.passport.TeacherInfoVo;
import sei.tk.service.passportO.UserInfoService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/3/25.
 * 教师信息相关
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Resource
    UserInfoService userInfoService;

    @RequestMapping("selfInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object selfInfo(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionTeacher");
        TeacherInfoVo teacherInfoVo=userInfoService.getInfoTeacher(sessionPassport.getPassportId());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",teacherInfoVo);
    }

    @RequestMapping("getInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_ADMIN)
    public Object getInfo(HttpSession session,Long passportId){//获取某个教师信息
        TeacherInfoVo teacherInfoVo=null;
        teacherInfoVo=userInfoService.getInfoTeacher(passportId);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",teacherInfoVo);
    }

    @RequestMapping("updateInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_ADMIN)
    public Object updateInfo(HttpSession session,TeacherInfoVo teacherInfoVo){//更新教师信息
        SessionPassport sessionPassport = (SessionPassport) session.getAttribute("sessionTeacher");
        teacherInfoVo.setPassportId(sessionPassport.getPassportId());
        userInfoService.updateTeacher(teacherInfoVo);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"修改信息成功",null);
    }
}
