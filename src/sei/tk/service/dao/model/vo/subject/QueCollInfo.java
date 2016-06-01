package sei.tk.service.dao.model.vo.subject;

/**
 * Created by ywl on 2016/6/1.
 */
public class QueCollInfo {
    private Long quecollId; //错题id
    private Long subjectId; //题目id
    private Long stuId; //学生id
    private String quecollAnswer; //错误答案
    private String courseName;  //课程名
    private String knopointName; //知识点名
    private Byte levelId; //难度id
    private String subjectAnswer; //正确答案
    private String subjectOption;//选项
    private String subjectName; //提干
    private String subjectSolution; //解析
    private Integer start;//用于分页
    private Integer row;//用于分页
    public Long getQuecollId() {
        return quecollId;
    }

    public void setQuecollId(Long quecollId) {
        this.quecollId = quecollId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getQuecollAnswer() {
        return quecollAnswer;
    }

    public void setQuecollAnswer(String quecollAnswer) {
        this.quecollAnswer = quecollAnswer;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getKnopointName() {
        return knopointName;
    }

    public void setKnopointName(String knopointName) {
        this.knopointName = knopointName;
    }

    public Byte getLevelId() {
        return levelId;
    }

    public void setLevelId(Byte levelId) {
        this.levelId = levelId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectSolution() {
        return subjectSolution;
    }

    public void setSubjectSolution(String subjectSolution) {
        this.subjectSolution = subjectSolution;
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

    public String getSubjectAnswer() {
        return subjectAnswer;
    }

    public void setSubjectAnswer(String subjectAnswer) {
        this.subjectAnswer = subjectAnswer;
    }

    public String getSubjectOption() {
        return subjectOption;
    }

    public void setSubjectOption(String subjectOption) {
        this.subjectOption = subjectOption;
    }
}
