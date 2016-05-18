package sei.tk.service.dao.model.vo.passport;

/**
 * Created by liuruijie on 2016/3/25.
 */
public class SessionPassport {
    private Long ppassportId;//账号id
    private String passportCode;//编号
    private String passportName;//姓名

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

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }
}
