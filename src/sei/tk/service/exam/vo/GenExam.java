package sei.tk.service.exam.vo;

/**
 * Created by liuruijie on 2016/6/4.
 */
public class GenExam extends MyExam{
    private Long passportId;
    private String stuName;
    private String stuCode;
    private Long stuClass;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    public Long getStuClass() {
        return stuClass;
    }

    public void setStuClass(Long stuClass) {
        this.stuClass = stuClass;
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }
}
