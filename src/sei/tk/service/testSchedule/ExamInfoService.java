package sei.tk.service.testSchedule;

import sei.tk.service.dao.model.vo.testSchedule.PageTestInfo;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;

import java.util.List;

/**
 * Created by 风中男子 on 2016-03-25.
 * 学生查看当前可进行/进行中的考试相关信息
 */
public interface ExamInfoService {
    PageTestInfo getAllPaperInfo(Long ppassportId,Integer page,Integer rows);   //老师得到所有试卷（无论安排与否）
    TestInfo getPaperInfoDetails(Long ppassportId,Long testscheduleId,Long testpaperId); //得到试卷属性细节
    PageTestInfo getAllExamInfo(Long ppassportId,Integer page,Integer rows);  //老师得到所有已安排试卷
    List<TestInfo> getFutureExamInfo(Long ppassportId); //得到还未开考的试卷的信息
    List<TestInfo> getValidExamInfo(Long ppassportId);   //得到可进行或进行中试卷的信息
    List<TestInfo> getOverdueExamInfo(Long ppassportId);   //得到过期试卷的信息
    List<TestInfo> getMyExam(Long passportId);
}
