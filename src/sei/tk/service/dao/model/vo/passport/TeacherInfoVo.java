package sei.tk.service.dao.model.vo.passport;

/**
 * Created by liuruijie on 2016/3/25.
 */
public class TeacherInfoVo extends SessionPassport{
    private String teaGender;
    private String teaInstitute;
    private String teaMajor;
    private String teaEmail;
    private String teaTel;

    public String getTeaGender() {
        return teaGender;
    }

    public void setTeaGender(String teaGender) {
        this.teaGender = teaGender;
    }

    public String getTeaInstitute() {
        return teaInstitute;
    }

    public void setTeaInstitute(String teaInstitute) {
        this.teaInstitute = teaInstitute;
    }

    public String getTeaMajor() {
        return teaMajor;
    }

    public void setTeaMajor(String teaMajor) {
        this.teaMajor = teaMajor;
    }

    public String getTeaEmail() {
        return teaEmail;
    }

    public void setTeaEmail(String teaEmail) {
        this.teaEmail = teaEmail;
    }

    public String getTeaTel() {
        return teaTel;
    }

    public void setTeaTel(String teaTel) {
        this.teaTel = teaTel;
    }
}
