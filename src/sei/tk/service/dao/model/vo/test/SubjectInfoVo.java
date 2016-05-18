package sei.tk.service.dao.model.vo.test;

import java.util.List;

/**
 * Created by liuruijie on 2016/3/12.
 * 考试时的题目信息
 */
public class SubjectInfoVo {
    private Long subjectId; //题目编号
    private Integer number;//显示的编号
    private String subjectName; //提干
    private List<String> subjectOptions; //选项
    private Integer score; //本题分数
    private Integer type; //题目类型
    private String stuAnswer; //学生答案
    private Integer intAnswer;//选择题整型答案，用于el表达式
    private Integer blankamount; //填空题的空数
    private String subjectSolution; //题目解析
    private String[] stuBlankAnswer; //填空题答案
    private Short courseId;//课程Id
    private Long knopointId; //知识点Id
    private Byte chapterId; //章节Id
    private String errAnw; //答错的答案

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

    public Byte getChapterId() {
        return chapterId;
    }

    public void setChapterId(Byte chapterId) {
        this.chapterId = chapterId;
    }

    public String getErrAnw() {
        return errAnw;
    }

    public void setErrAnw(String errAnw) {
        this.errAnw = errAnw;
    }

    public String getSubjectSolution() {
        return subjectSolution;
    }

    public void setSubjectSolution(String subjectSolution) {
        this.subjectSolution = subjectSolution;
    }

    public Integer getBlankamount() {
        return blankamount;
    }

    public void setBlankamount(Integer blankamount) {
        this.blankamount = blankamount;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<String> getSubjectOptions() {
        return subjectOptions;
    }

    public void setSubjectOptions(List<String> subjectOptions) {
        this.subjectOptions = subjectOptions;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(String stuAnswer) {
        this.stuAnswer = stuAnswer;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String[] getStuBlankAnswer() {
        return stuBlankAnswer;
    }

    public void setStuBlankAnswer(String[] stuBlankAnswer) {
        this.stuBlankAnswer = stuBlankAnswer;
    }

    public Integer getIntAnswer() {
        return intAnswer;
    }

    public void setIntAnswer(Integer intAnswer) {
        this.intAnswer = intAnswer;
    }
}
