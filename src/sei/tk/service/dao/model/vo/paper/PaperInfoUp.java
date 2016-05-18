package sei.tk.service.dao.model.vo.paper;

import java.util.Date;

/**
 * Created by ywl on 2016/5/4.
 */
public class PaperInfoUp {
    private String subjectName;
    private String subjectOption;
    private String subjectSolution;
    private Short testpaperScore;
    private Long subjectId;
    private Short courseId;
    private Long knopointId;
    private Short quetypeId;
    private Byte chapterId;
    private Byte levelId;
    private String subjectAnswer;
    private Long ppassportId;
    private Date ppassportTime;
    private String ppassportName;
    private String ppassportTimeString;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectOption() {
        return subjectOption;
    }

    public void setSubjectOption(String subjectOption) {
        this.subjectOption = subjectOption;
    }

    public String getSubjectSolution() {
        return subjectSolution;
    }

    public void setSubjectSolution(String subjectSolution) {
        this.subjectSolution = subjectSolution;
    }

    public Short getTestpaperScore() {
        return testpaperScore;
    }

    public void setTestpaperScore(Short testpaperScore) {
        this.testpaperScore = testpaperScore;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Short getCourseId() {
        return courseId;
    }

    public void setCourseId(Short courseId) {
        this.courseId = courseId;
    }

    public Long getKnopointId() {
        return knopointId;
    }

    public void setKnopointId(Long knopointId) {
        this.knopointId = knopointId;
    }

    public Short getQuetypeId() {
        return quetypeId;
    }

    public void setQuetypeId(Short quetypeId) {
        this.quetypeId = quetypeId;
    }

    public Byte getChapterId() {
        return chapterId;
    }

    public void setChapterId(Byte chapterId) {
        this.chapterId = chapterId;
    }

    public Byte getLevelId() {
        return levelId;
    }

    public void setLevelId(Byte levelId) {
        this.levelId = levelId;
    }

    public String getSubjectAnswer() {
        return subjectAnswer;
    }

    public void setSubjectAnswer(String subjectAnswer) {
        this.subjectAnswer = subjectAnswer;
    }

    public Long getPpassportId() {
        return ppassportId;
    }

    public void setPpassportId(Long ppassportId) {
        this.ppassportId = ppassportId;
    }

    public Date getPpassportTime() {
        return ppassportTime;
    }

    public void setPpassportTime(Date ppassportTime) {
        this.ppassportTime = ppassportTime;
    }

    public String getPpassportName() {
        return ppassportName;
    }

    public void setPpassportName(String ppassportName) {
        this.ppassportName = ppassportName;
    }

    public String getPpassportTimeString() {
        return ppassportTimeString;
    }

    public void setPpassportTimeString(String ppassportTimeString) {
        this.ppassportTimeString = ppassportTimeString;
    }
}
