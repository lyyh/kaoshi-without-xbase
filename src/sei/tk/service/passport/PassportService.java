package sei.tk.service.passport;

import sei.tk.service.common.ResourceService;
import sei.tk.service.dao.model.Passport;

/**
 * Created by liuruijie on 2016/5/22.
 */
public interface PassportService extends ResourceService<Passport> {
    void changePsw(long passportId, String oldPsw, String newPsw);
}
