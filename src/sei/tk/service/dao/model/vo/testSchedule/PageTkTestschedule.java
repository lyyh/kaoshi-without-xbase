package sei.tk.service.dao.model.vo.testSchedule;

import java.util.Date;

/**
 * Created by ·çÖÐÄÐ×Ó on 2016-05-02.
 */
public class PageTkTestschedule {
        private Long testscheduleId;
        private Long ppassportId;
        private Long testpaperId;
        private String testStarttime;
        private String testEndtime;
        private Short courseId;
        private Short stuScore;
        private Short stuBasescore;
        private Long createPpassportId;
        private String createTime;

    public Long getTestscheduleId() {
        return testscheduleId;
    }

    public void setTestscheduleId(Long testscheduleId) {
        this.testscheduleId = testscheduleId;
    }

    public Long getPpassportId() {
        return ppassportId;
    }

    public void setPpassportId(Long ppassportId) {
        this.ppassportId = ppassportId;
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

    public Long getCreatePpassportId() {
        return createPpassportId;
    }

    public void setCreatePpassportId(Long createPpassportId) {
        this.createPpassportId = createPpassportId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
