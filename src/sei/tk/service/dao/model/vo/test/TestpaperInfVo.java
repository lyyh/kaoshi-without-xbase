package sei.tk.service.dao.model.vo.test;

import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2016/3/12.
 * 考试时的试卷信息
 */
public class TestpaperInfVo {
    private Long testpaperId; //试卷编号
    private String course; //学科
    private Integer totalScore; //总分
    private List<TypeAmount> subAmount; //各题型数量
    private String term; //学期
    private Integer totalTime; //考试总时长
    private Integer totalSubAmount;//总题数

    public Long getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Long testpaperId) {
        this.testpaperId = testpaperId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTotalSubAmount() {
        return totalSubAmount;
    }

    public void setTotalSubAmount(Integer totalSubAmount) {
        this.totalSubAmount = totalSubAmount;
    }

    public List<TypeAmount> getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(List<TypeAmount> subAmount) {
        this.subAmount = subAmount;
    }
}
