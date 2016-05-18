package sei.tk.service.dao.model.vo.subject;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by liuruijie on 2016/4/10.
 */
public class SubjectInfo {
    private Long subjectId; //题目编号
    private String subjectName; //提干
    private String options;//数据库存储的选项
    private String[] subjectOptions; //选项
    private Integer type; //题目类型
    private String answer; //答案
    private String subjectSolution; //题目解析
    private String[] blankAnswer; //填空题答案
    private Byte levelId;//题目难度
    private Short courseId;//科目id
    private String course;//科目
    private Long knopointId;//知识点id
    private String knopoint; //知识点
    private Byte chapterId; //章节
    private String passportName;//出题人姓名
    private Long passportId;//出题人id
    private Date createTime;//创建时间
    private String createTimeString;//时间字符串
    private Integer start;//用于分页
    private Integer row;//用于分页

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String[] getSubjectOptions() {
        return subjectOptions;
    }

    public void setSubjectOptions(String[] subjectOptions) {
        this.subjectOptions = subjectOptions;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSubjectSolution() {
        return subjectSolution;
    }

    public void setSubjectSolution(String subjectSolution) {
        this.subjectSolution = subjectSolution;
    }

    public String[] getBlankAnswer() {
        return blankAnswer;
    }

    public void setBlankAnswer(String[] blankAnswer) {
        this.blankAnswer = blankAnswer;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getKnopoint() {
        return knopoint;
    }

    public void setKnopoint(String knopoint) {
        this.knopoint = knopoint;
    }

    public Byte getChapterId() {
        return chapterId;
    }

    public void setChapterId(Byte chapterId) {
        this.chapterId = chapterId;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
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

    public Byte getLevelId() {
        return levelId;
    }

    public void setLevelId(Byte levelId) {
        this.levelId = levelId;
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }
}
