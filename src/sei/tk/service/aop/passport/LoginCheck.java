package sei.tk.service.aop.passport;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.model.vo.passport.SessionPassport;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuruijie on 2016/4/11.
 * 处理登陆校验，权限校验
 */
@Aspect
@Service
public class LoginCheck {

    @Around("@annotation(loginState)")
    public Object loginCheckStudent(ProceedingJoinPoint joinPoint,NeedLogin loginState) throws Throwable {
        Object[] args=joinPoint.getArgs();
        HttpSession session= (HttpSession) args[0];
        SessionPassport sessionTeacher= (SessionPassport) session.getAttribute("sessionTeacher");
        SessionPassport sessionStudent= (SessionPassport) session.getAttribute("sessionStudent");
        //获取当前session中的账号信息
        Map<String,Boolean> roleMap=new HashMap<String,Boolean>();
        if(sessionTeacher!=null)roleMap.put(TkConfig.ROLE_TEACHER,true);
        if(sessionStudent!=null)roleMap.put(TkConfig.ROLE_STUDENT,true);
        if(session.getAttribute("SessionUser")!=null)roleMap.put(TkConfig.ROLE_ADMIN,true);
        if(roleMap.size()==0){
            return LittleUtil.constructResponse(TkConfig.NO_LOGIN,"请先登录",null);
        }
        //遍历注解上的角色，满足一个就执行
        for(String needRole:loginState.value()){
            if(roleMap.get(needRole)!=null)return joinPoint.proceed();
        }
        return LittleUtil.constructResponse(TkConfig.NO_PRIVILEGE,"没有执行该操作的权限",null);
    }
}
