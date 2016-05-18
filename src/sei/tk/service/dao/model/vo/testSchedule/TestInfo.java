package sei.tk.service.dao.model.vo.testSchedule;

import java.util.Date;

/**
 * Created by 风中男子 on 2016-03-25.
 * 考试安排信息中的，和当前用户相关的试卷的信息
 */
public class TestInfo {
    private Long testscheduleId; //安排编号

    private Long testpaperId;   //试卷编号
    private String testStarttime; //考试开始时间
    private String testEndtime;   //考试结束时间

    private String newStarttime;    //用于修改考试安排

    private String newEndtime;

    private Short courseId;     //课程编号（防止同名课程）
    private Short stuScore;     //最终分数
    private Short stuBasescore; //基础分数（选择填空判断题）

    private String courseName;  //课程名称

    private Short testpaperType;    //试卷类型,默认0（考试试卷）1（模拟试卷）,由boolean转

    private String mkpaperTerm; //考试学期
    private Short mkpaperExtime;//考试时长
    private Short mkpaperScore; //试卷总分

    private Long createPpassportId;   //出卷人、安排创建人编号
    private String teaName;     //出卷人姓名

    public String getNewStarttime() {
        return newStarttime;
    }

    public void setNewStarttime(String newStarttime) {
        this.newStarttime = newStarttime;
    }

    public String getNewEndtime() {
        return newEndtime;
    }

    public void setNewEndtime(String newEndtime) {
        this.newEndtime = newEndtime;
    }

    public Long getCreatePpassportId() {
        return createPpassportId;
    }

    public void setCreatePpassportId(Long createPpassportId) {
        this.createPpassportId = createPpassportId;
    }

    public Long getTestscheduleId() {
        return testscheduleId;
    }

    public void setTestscheduleId(Long testscheduleId) {
        this.testscheduleId = testscheduleId;
    }

    public Long getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Long testpaperId) {
        this.testpaperId = testpaperId;
    }

    public String getTestStarttime() {
        return testStarttime;
    }

    public void setTestStarttime(String testStarttime) {
        this.testStarttime = testStarttime;
    }

    public String getTestEndtime() {
        return testEndtime;
    }

    public void setTestEndtime(String testEndtime) {
        this.testEndtime = testEndtime;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Short getStuScore() {
        return stuScore;
    }

    public void setStuScore(Short stuScore) {
        this.stuScore = stuScore;
    }

    public Short getStuBasescore() {
        return stuBasescore;
    }

    public void setStuBasescore(Short stuBasescore) {
        this.stuBasescore = stuBasescore;
    }

    public Short getTestpaperType() {
        return testpaperType;
    }

    public void setTestpaperType(Short testpaperType) {
        this.testpaperType = testpaperType;
    }

    public String getMkpaperTerm() {
        return mkpaperTerm;
    }

    public void setMkpaperTerm(String mkpaperTerm) {
        this.mkpaperTerm = mkpaperTerm;
    }

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

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }
}
