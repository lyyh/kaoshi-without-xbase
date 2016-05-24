package sei.tk.service.passport.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.PassportMapper;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.passport.LoginService;
import sei.tk.service.passport.vo.LoginForm;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;

/**
 * Created by liuruijie on 2016/5/16.
 */
@Service
public class LoginImpl implements LoginService {
    @Resource
    PassportMapper passportMapper;

    @Override
    public SessionPassport doLogin(LoginForm loginForm) {
        SessionPassport sessionPassport=null;
        String result=passportMapper.doLogin(loginForm);
        if(TkConfig.IP_NOT_ACCESS.equals(result)){
            throw new TKException(TkConfig.INVALID_ACTION,"ip不合法，拒绝登陆");
        }else if(TkConfig.MULTI_PLACE_LOGIN.equals(result)){
            throw new TKException(TkConfig.INVALID_ACTION,"该账号已在其他地方登陆");
        }else if("".equals(result)){
            throw new TKException(TkConfig.INVALID_ACTION,"账号或密码错误");
        }else{
            sessionPassport= JSON.parseObject(result, SessionPassport.class);
        }
        return sessionPassport;
    }

    @Override
    public void doLogout(long passportId) {
        if(passportMapper.doLogout(passportId)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"失败");
        }
    }
}
