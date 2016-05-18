package sei.tk.service.dao.model.vo.testSchedule;

import sei.tk.service.dao.model.TkMkpaper;

/**
 * Created by 风中男子 on 2016-03-26.
 * 制卷信息表——改
 */
public class Mkpaper extends TkMkpaper {
    private String courseName;  //课程名称
    private String teaName; //出卷老师姓名

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }
}
