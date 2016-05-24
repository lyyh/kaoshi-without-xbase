package sei.tk.service.dao.model;

import sei.tk.service.dao.model.common.ResourceVo;

/**
 * Created by liuruijie on 2016/5/13.
 */
public class Access extends ResourceVo {
    //操作主语类型
    private String masterType;
    //操作主语
    private Integer masterId;
    //操作对象类型
    private String targetType;
    //操作对象
    private Integer targetId;

    private Integer targetModuleId;
    private Integer targetActionId;
    //权限
    private String privilegeIds;

    private String masterName;
    private String targetModuleName;
    private String targetActionName;

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public Integer getTargetModuleId() {
        return targetModuleId;
    }

    public void setTargetModuleId(Integer targetModuleId) {
        this.targetModuleId = targetModuleId;
    }

    public Integer getTargetActionId() {
        return targetActionId;
    }

    public void setTargetActionId(Integer targetActionId) {
        this.targetActionId = targetActionId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getTargetModuleName() {
        return targetModuleName;
    }

    public void setTargetModuleName(String targetModuleName) {
        this.targetModuleName = targetModuleName;
    }

    public String getTargetActionName() {
        return targetActionName;
    }

    public void setTargetActionName(String targetActionName) {
        this.targetActionName = targetActionName;
    }
}
