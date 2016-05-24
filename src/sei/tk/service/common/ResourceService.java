package sei.tk.service.common;

import sei.tk.service.dao.model.common.ResourceVo;
import sei.tk.util.Page;

/**
 * Created by liuruijie on 2016/5/17.
 */
public interface ResourceService<T extends ResourceVo> {
    Page list(Integer currentPage, Integer rows, T resourceVo);
    T show(Integer id);
    int update(T resourceVo);
    int delete(Integer id);
    int add(T resourceVo);
}
