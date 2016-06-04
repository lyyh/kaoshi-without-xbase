package sei.tk.service.exam;

import sei.tk.service.exam.vo.Analyse;
import sei.tk.service.exam.vo.GenExam;
import sei.tk.service.exam.vo.MyExam;
import sei.tk.util.Page;

import java.util.List;

/**
 * Created by liuruijie on 2016/6/2.
 */
public interface AfterExamService {
    List<MyExam> listMyExam(Long passportId);

    Page listAllGradsByPage(Integer currentPage, Integer rows, GenExam genExam);

    Analyse getAnalyse(Long testpaperId, Long passportId);
}
