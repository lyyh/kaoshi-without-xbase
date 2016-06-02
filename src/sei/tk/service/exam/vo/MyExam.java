package sei.tk.service.exam.vo;

/**
 * Created by liuruijie on 2016/6/2.
 */
public class MyExam {
    private Long scheduleId;
    private Long courseId;
    private String courseName;
    private String term;
    private Short score;
    private Short maxScore;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

    public Short getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Short maxScore) {
        this.maxScore = maxScore;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
