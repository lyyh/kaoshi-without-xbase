package sei.tk.service.exam.component;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;

/**
 * Created by liuruijie on 2016/4/7.
 * 考试题目检验者
 */
public interface SubjectChecker {
    SubjectInfoVo subjectPoToVo(TkSubject tkSubject,long testpaperId);//将po中的信息转到vo中
    int checkSubjects(StuAnswerVo stuAnswerVo);//检验题目并返回选择判断题分数
    void TempAnswer(StuAnswerVo stuAnswerVo,int number,String stuAnswer,String[] blankAnswers);//记录学生临时答案
}
