package sei.tk.service.exam.component;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.exam.ExamMapForRedis;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;

import java.util.List;

/**
 * Created by liuruijie on 2016/4/7.
 * 考试试卷提供者
 */
public interface PaperProvider {
    TestpaperInfVo obtainTestPaper(long testpaperId);//根据试卷id拿试卷
    SubjectInfoVo pickSubject(long stuId,int number,ExamMapForRedis examMapForRedis);//从试卷中拿题
    boolean isPaperAvailable(long stuId,long testpaperId);//判断试卷是否可用
    List<TkSubject> paperSubjects(long testpaperId);//获取试卷中所有题目
}
