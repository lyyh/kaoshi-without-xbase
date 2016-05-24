package sei.tk.service.module;

import sei.tk.service.dao.model.Module;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.module.vo.TreeMenu;

import java.util.List;

/**
 * Created by liuruijie on 2016/5/17.
 */
public interface ModuleService {
    List<TreeMenu> menuList(SessionPassport sessionPassport);
}
