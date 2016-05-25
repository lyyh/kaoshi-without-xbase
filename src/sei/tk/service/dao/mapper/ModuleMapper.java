package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by liuruijie on 2016/5/17.
 */
public interface ModuleMapper {
    String menuList(@Param("roleId") Integer roleId
            , @Param("groupId") Integer groupId
            , @Param("passportId") Integer passportId);
}
