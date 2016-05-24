package sei.tk.service.aop.passport;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.model.SessionPassport;
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
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        if(sessionPassport==null){
            return LittleUtil.constructResponse(TkConfig.NO_LOGIN,"您还未登陆",null);
        }
        int role=sessionPassport.getRoleId();
        for(int r:loginState.value()){
            if(role==r){
                return joinPoint.proceed();
            }
        }
        return LittleUtil.constructResponse(TkConfig.NO_PRIVILEGE,"没有执行该操作的权限",null);
    }
}
