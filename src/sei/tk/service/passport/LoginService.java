package sei.tk.service.passport;

import sei.tk.service.dao.model.vo.passport.Passport;
import sei.tk.service.dao.model.vo.passport.SessionPassport;

/**
 * Created by liuruijie on 2016/3/25.
 */
public interface LoginService {
    SessionPassport doLoginStudent(Passport passport);
    SessionPassport doLoginTeacher(Passport passport);
}
