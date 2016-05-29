package sei.tk.service.dao.model.vo.course;

/**
 * Created by lenovo on 2016/5/29.
 */
public class CourseVo  {
    private Short courseId;
    private String courseName;
    private String TkKnopoint;
    private Integer start=0;
    private Integer amount=10;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Short getCourseId() {
        return courseId;
    }

    public void setCourseId(Short courseId) {
        this.courseId = courseId;
    }

    public String getTkKnopoint() {
        return TkKnopoint;
    }

    public void setTkKnopoint(String tkKnopoint) {
        TkKnopoint = tkKnopoint;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
