package sei.tk.controller.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.passport.StudentInfoVo;
import sei.tk.service.passportO.UserInfoService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/3/25.
 * 学生信息相关
 */
@Controller
@RequestMapping("student")
public class StudentController extends TkBaseController{
    @Resource
    UserInfoService userInfoService;

    @RequestMapping("selfInfo")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object selfInfo(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionStudent");
        StudentInfoVo studentInfoVo=userInfoService.getInfoStudent(sessionPassport.getPassportId());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",studentInfoVo);
    }

    @RequestMapping("getInfo")
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_TEACHER,TkConfig.ROLE_ADMIN})
    public Object getInfo(HttpSession session,Long passportId){//获取某个学生信息
        StudentInfoVo studentInfoVo=userInfoService.getInfoStudent(passportId);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",studentInfoVo);
    }

    @RequestMapping("updateInfo")
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_ADMIN,TkConfig.ROLE_TEACHER})
    public Object updateInfo(HttpSession session,StudentInfoVo studentInfoVo){//更新学生信息
        SessionPassport sessionPassport = (SessionPassport) session.getAttribute("sessionStudent");
        if(sessionPassport!=null)studentInfoVo.setPassportId(sessionPassport.getPassportId());
        userInfoService.updateStudent(studentInfoVo);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"更新信息成功",null);
    }
}
