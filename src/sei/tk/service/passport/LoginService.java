package sei.tk.service.passport;

import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.passport.vo.LoginForm;

/**
 * Created by liuruijie on 2016/5/16.
 */
public interface LoginService {
    SessionPassport doLogin(LoginForm loginForm);
    void doLogout(long passportId);
}
