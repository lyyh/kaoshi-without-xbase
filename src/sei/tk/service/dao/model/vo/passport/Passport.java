package sei.tk.service.dao.model.vo.passport;

import java.util.Date;

/**
 * Created by liuruijie on 2016/3/25.
 */
public class Passport {
    private Long ppassportId;
    private String passportCode;
    private String password;
    private Date passportCreatetime;
    private Boolean passportIsonline;

    public Long getPpassportId() {
        return ppassportId;
    }

    public void setPpassportId(Long ppassportId) {
        this.ppassportId = ppassportId;
    }

    public String getPassportCode() {
        return passportCode;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPassportCreatetime() {
        return passportCreatetime;
    }

    public void setPassportCreatetime(Date passportCreatetime) {
        this.passportCreatetime = passportCreatetime;
    }

    public Boolean getPassportIsonline() {
        return passportIsonline;
    }

    public void setPassportIsonline(Boolean passportIsonline) {
        this.passportIsonline = passportIsonline;
    }
}
