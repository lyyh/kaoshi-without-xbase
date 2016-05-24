package sei.tk.service.dao.model;

import sei.tk.service.dao.model.common.ResourceVo;
import sei.tk.util.DateFormat;

import java.util.Date;

/**
 * Created by liuruijie on 2016/5/22.
 */
public class Passport extends ResourceVo {

    private String passportPsw;

    private Integer loginCount;

    private Date lastLoginTime;
    private String lastLoginTimeString;

    private String lastLoginIp;

    private Byte isOnline;

    private String limitIp;

    public String getPassportPsw() {
        return passportPsw;
    }

    public void setPassportPsw(String passportPsw) {
        this.passportPsw = passportPsw;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTimeString= DateFormat.DateToString(lastLoginTime, "yyyy-MM-dd HH:mm:ss");
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLimitIp() {
        return limitIp;
    }

    public void setLimitIp(String limitIp) {
        this.limitIp = limitIp;
    }

    public String getLastLoginTimeString() {
        return lastLoginTimeString;
    }

    public void setLastLoginTimeString(String lastLoginTimeString) {
        this.lastLoginTimeString = lastLoginTimeString;
    }

    public Byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Byte isOnline) {
        this.isOnline = isOnline;
    }
}
