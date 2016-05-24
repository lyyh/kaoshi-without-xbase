package sei.tk.util;

import sei.tk.service.dao.model.common.ResourceVo;

import java.util.List;

/**
 * Created by liuruijie on 2016/5/17.
 */
public interface ResourceMapper<T extends ResourceVo> {
    List<T> selectByPage(T resourceVo);

    int countForPage(T resourceVo);

    T selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(T resourceVo);

    int updateByPrimaryKeySelective(T resourceVo);
}
