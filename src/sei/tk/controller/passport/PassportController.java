package sei.tk.controller.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.vo.common.ResultVo;
import sei.tk.service.dao.model.vo.passport.Passport;
import sei.tk.service.dao.model.vo.passport.SessionPassport;
import sei.tk.service.passport.LoginService;
import sei.tk.service.passport.PassportServie;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/3/25.
 * 处理账号相关
 */
@Controller
@RequestMapping("passport")
public class PassportController extends TkBaseController{
    @Resource
    LoginService loginService;
    @Resource
    PassportServie passportServie;

    @RequestMapping("login")
    @ResponseBody
    public Object doLogin(HttpSession session,String username,String password){
        SessionPassport sessionPassport=null;
        //绑定账号信息
        Passport passport=new Passport();
        passport.setPassportCode(username);
        passport.setPassword(password);

//        if("teacher".equals(type)){//教师登陆
//            sessionPassport=loginService.doLoginTeacher(passport);
//            session.setAttribute("sessionTeacher", sessionPassport);
//        }else if("student".equals(type)){//学生登陆
//            sessionPassport=loginService.doLoginStudent(passport);
//            session.setAttribute("sessionStudent", sessionPassport);
//        }
        String role="";
        if(passportServie.isStudentCode(username)){
            sessionPassport=loginService.doLoginStudent(passport);
            session.setAttribute("sessionStudent",sessionPassport);
            session.removeAttribute("sessionTeacher");
            role="student";
        }else if(passportServie.isTeacherCode(username)){
            sessionPassport=loginService.doLoginTeacher(passport);
            session.setAttribute("sessionTeacher",sessionPassport);
            session.removeAttribute("sessionStudent");
            role="teacher";
        }else{
            throw new TKException(TkConfig.INVALID_ACTION,"账号或密码错误");
        }
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",role);
    }

    @RequestMapping("logout")
    @ResponseBody
    public Object logout(HttpSession session){
        session.removeAttribute("sessionTeacher");
        session.removeAttribute("sessionStudent");
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",null);
    }

    @RequestMapping("addStudent")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_ADMIN)
    public Object addStudent(TkStudent tkStudent){//添加学生账号
        passportServie.addStudent(tkStudent);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"添加学生成功",null);
    }

    @RequestMapping("addTeacher")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_ADMIN)
    public Object addTeacher(TkTeacher tkTeacher){//添加教师账号
        passportServie.addTeacher(tkTeacher);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"添加教师成功",null);
    }

    @RequestMapping("/teacher/changePsw")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object changePswTeacher(HttpSession session,String oldpsw,String newpsw){//修改教师密码
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionTeacher");
        passportServie.modifyPasswordTeacher(sessionPassport.getPpassportId(),oldpsw,newpsw);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"密码修改成功",null);
    }

    @RequestMapping("/student/changePsw")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object changePswStudent(HttpSession session,String oldpsw,String newpsw){//修改学生密码
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionStudent");
        passportServie.modifyPasswordStudent(sessionPassport.getPpassportId(),oldpsw,newpsw);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"密码修改成功",null);
    }

    @RequestMapping("selfPassportInfo")
    @ResponseBody
    @NeedLogin({TkConfig.ROLE_TEACHER,TkConfig.ROLE_STUDENT})
    public Object selfPass(HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionStudent");
        if(sessionPassport==null){
            sessionPassport=(SessionPassport) session.getAttribute("sessionTeacher");
        }
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",sessionPassport);
    }
}
