package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.Access;
import sei.tk.util.ResourceMapper;

/**
 * Created by liuruijie on 2016/5/16.
 */
public interface AccessMapper extends ResourceMapper<Access> {
    String getPrivileges(
            @Param("moduleId") Integer moduleId
            , @Param("actionId") Integer actionId
            , @Param("roleId") Integer roleId
            , @Param("groupId") Integer groupId
            , @Param("passportId") Integer passportId
            , @Param("type") String type
    );
    String getPrivilegeRequire(
            @Param("moduleId") Integer moduleId
            , @Param("resourceId") Integer resourceId
            , @Param("passportId") Integer passportId
    );
}
