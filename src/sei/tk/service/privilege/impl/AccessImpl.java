package sei.tk.service.privilege.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.common.ResourceServiceAdapter;
import sei.tk.service.dao.mapper.AccessMapper;
import sei.tk.service.dao.model.Access;
import sei.tk.service.privilege.AccessService;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;

/**
 * Created by liuruijie on 2016/5/16.
 */
@Service
public class AccessImpl extends ResourceServiceAdapter<AccessMapper,Access> implements AccessService {
    @Resource
    AccessMapper accessMapper;

    @Override
    public int update(Access access) {
        Access access1=accessMapper.selectByPage(access).get(0);
        if(access1.getMasterType().equals("role")&&access1.getMasterId()==1){
            throw new TKException(TkConfig.NO_PRIVILEGE,"不能修改超级管理员的权限");
        }
        return accessMapper.updateByPrimaryKeySelective(access);
    }

    @Override
    public int delete(Integer accessId) {
        Access access=new Access();
        access.setId(accessId);
        access=accessMapper.selectByPage(access).get(0);
        if(access.getMasterType().equals("role")&&access.getMasterId()==1){
            throw new TKException(TkConfig.NO_PRIVILEGE,"不能修改超级管理员的权限");
        }
        return accessMapper.deleteByPrimaryKey(accessId);
    }

    @Override
    public int add(Access access) {
        if(access.getMasterType().equals("role")&&access.getMasterId()==1){
            throw new TKException(TkConfig.NO_PRIVILEGE,"不能修改超级管理员的权限");
        }
        return accessMapper.insertSelective(access);
    }
}
