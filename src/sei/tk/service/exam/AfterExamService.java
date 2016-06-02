package sei.tk.service.exam;

import sei.tk.service.exam.vo.MyExam;

import java.util.List;

/**
 * Created by liuruijie on 2016/6/2.
 */
public interface AfterExamService {
    List<MyExam> listMyExam(Long passportId);
}
