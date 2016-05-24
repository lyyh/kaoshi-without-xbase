package sei.tk.service.dao.model.common;

import sei.tk.util.DateFormat;

import java.util.Date;

/**
 * Created by liuruijie on 2016/5/13.
 */
public class ResourceVo extends Pagination{
    protected Integer id;//主键
    protected String name;//资源名
    protected Byte status;//状态
    protected Integer passportId;//所属人
    private String passportName;//创建人名称
    protected Date createTime;//创建时间
    private String createTimeString;//创建时间格式化字符串

    protected Integer moduleId;//模块
    protected Integer actionId;//操作

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createTimeString= DateFormat.DateToString(createTime, "yyyy-MM-dd HH:mm:ss");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }
}
