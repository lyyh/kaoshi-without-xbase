package sei.tk.service.dao.model.vo.study;

import sei.tk.service.dao.model.vo.test.SubjectInfoVo;

import java.util.Map;

/**
 * Created by Administrator on 2016/3/18 0018.
 */

/**
 * 学生测试时的情况
 */
public class StudyAnswerVo {
    private Long stuId; //学号
    private Map<Long,SubjectInfoVo> answers; //学生答案
    private int answersAmount; //答题数量
    private boolean isFinished; //是否完成试卷

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Map<Long, SubjectInfoVo> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, SubjectInfoVo> answers) {
        this.answers = answers;
    }

    public int getAnswersAmount() {
        return answersAmount;
    }

    public void setAnswersAmount(int answersAmount) {
        this.answersAmount = answersAmount;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
