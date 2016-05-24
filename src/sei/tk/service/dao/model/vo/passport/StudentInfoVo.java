package sei.tk.service.dao.model.vo.passport;

import sei.tk.service.dao.model.SessionPassport;

/**
 * Created by liuruijie on 2016/3/25.
 */
public class StudentInfoVo extends SessionPassport {
    private String stuGender;
    private String stuInstitute;
    private String stuMajor;
    private Long stuClassId;
    private String stuEmail;
    private String stuTel;

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    public String getStuInstitute() {
        return stuInstitute;
    }

    public void setStuInstitute(String stuInstitute) {
        this.stuInstitute = stuInstitute;
    }

    public String getStuMajor() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor = stuMajor;
    }

    public Long getStuClassId() {
        return stuClassId;
    }

    public void setStuClassId(Long stuClassId) {
        this.stuClassId = stuClassId;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getStuTel() {
        return stuTel;
    }

    public void setStuTel(String stuTel) {
        this.stuTel = stuTel;
    }
}
