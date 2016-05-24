package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.Passport;
import sei.tk.service.passport.vo.LoginForm;
import sei.tk.util.ResourceMapper;

/**
 * Created by liuruijie on 2016/5/16.
 */
public interface PassportMapper extends ResourceMapper<Passport> {
    String doLogin(LoginForm loginForm);
    int doLogout(long passportId);
    String changePsw(@Param("id") long id
            , @Param("oldPsw") String oldPsw
            , @Param("newPsw") String newPsw);
}
