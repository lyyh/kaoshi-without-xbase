package sei.tk.service.testSchedule;

import sei.tk.service.dao.model.vo.testSchedule.TestInfo;

/**
 * Created by 风中男子 on 2016-04-22.
 * 老师增加、删除、修改安排考试
 */
public interface TeaSchService {
    Integer addExamSch(Long ppassportId, TestInfo testInfo);  //老师添加考试安排（暂给所有学生发送安排）
    Integer delExamSch(Long ppassportId,Long testscheduleId);  //老师删除考试安排（根据安排编号）
    Integer editExamSch(Long ppassportId,TestInfo testInfo);    //老师修改考试安排（根据安排编号）
}
