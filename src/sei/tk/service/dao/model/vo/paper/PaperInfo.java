package sei.tk.service.dao.model.vo.paper;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;

import java.util.Date;
import java.util.List;

/**
 * Created by ywl on 2016/4/12.
 * 用于试卷管理
 */
public class PaperInfo {
    private Long testpaperId;   //试卷编号
    private Long mkpaperId;     //制卷编号
    private Short courseId;     //课程编号
    private String courseName;    //课程名字
    private String mkpaperTerm; //考试学期
    private Long ppassportId;   //出题人编号
    private String passportName;//出题人姓名
    private Date ppassportTime; //制卷时间(创建)
    private String TimeString;//纸卷时间（字符串）
    private String paperName; //试卷名字
    private Short mkpaperScore;//试卷总分
    private Short mkpaperExtime; //考试时长
    private List<TkSubjectWithBLOBs> subjects  ;//试卷的题目

    private Integer start;//用于分页
    private Integer row;//用于分页


    public Short getMkpaperExtime() {
        return mkpaperExtime;
    }

    public void setMkpaperExtime(Short mkpaperExtime) {
        this.mkpaperExtime = mkpaperExtime;
    }

    public Short getMkpaperScore() {
        return mkpaperScore;
    }

    public void setMkpaperScore(Short mkpaperScore) {
        this.mkpaperScore = mkpaperScore;
    }

    public List<TkSubjectWithBLOBs> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TkSubjectWithBLOBs> subjects) {
        this.subjects = subjects;
    }

    public String getTimeString() {
        return TimeString;
    }

    public void setTimeString(String timeString) {
        TimeString = timeString;
    }

    public Long getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Long testpaperId) {
        this.testpaperId = testpaperId;
    }

    public Long getMkpaperId() {
        return mkpaperId;
    }

    public void setMkpaperId(Long mkpaperId) {
        this.mkpaperId = mkpaperId;
    }

    public Short getCourseId() {
        return courseId;
    }

    public void setCourseId(Short courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String course) {
        this.courseName = course;
    }

    public String getMkpaperTerm() {
        return mkpaperTerm;
    }

    public void setMkpaperTerm(String mkpaperTerm) {
        this.mkpaperTerm = mkpaperTerm;
    }

    public Long getPpassportId() {
        return ppassportId;
    }

    public void setPpassportId(Long ppassportId) {
        this.ppassportId = ppassportId;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public Date getPpassportTime() {
        return ppassportTime;
    }

    public void setPpassportTime(Date ppassportTime) {
        this.ppassportTime = ppassportTime;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }



}
