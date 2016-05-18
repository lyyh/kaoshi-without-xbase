package sei.tk.service.dao.model.vo.test;

import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2016/3/12.
 * 学生的答案与试卷完成情况
 */
public class StuAnswerVo {
    private Long stuId; //学号
    private List<SubjectInfoVo> answers; //学生答案
    private int answersAmount; //答题数量

    public int getAnswersAmount() {
        return answersAmount;
    }

    public void setAnswersAmount(int answersAmount) {
        this.answersAmount = answersAmount;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public List<SubjectInfoVo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SubjectInfoVo> answers) {
        this.answers = answers;
    }
}
