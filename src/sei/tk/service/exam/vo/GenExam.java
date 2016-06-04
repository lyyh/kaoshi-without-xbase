package sei.tk.service.exam.vo;

/**
 * Created by liuruijie on 2016/6/4.
 */
public class GenExam extends MyExam{
    private String stuName;
    private String stuCode;
    private String stuClass;

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

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }
}
