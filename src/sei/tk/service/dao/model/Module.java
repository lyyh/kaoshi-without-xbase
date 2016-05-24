package sei.tk.service.dao.model;

import sei.tk.service.dao.model.common.ResourceVo;

/**
 * Created by liuruijie on 2016/5/13.
 */
public class Module extends ResourceVo{
    private String moduleName;

    private String moduleCode;

    private Integer modulePid;

    private String moduleUrl;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getModulePid() {
        return modulePid;
    }

    public void setModulePid(Integer modulePid) {
        this.modulePid = modulePid;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }
}
