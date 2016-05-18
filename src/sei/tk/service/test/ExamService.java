package sei.tk.service.test;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;

import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/3/12.
 */
public interface ExamService {
    TestpaperInfVo initExam(HttpSession session, Long testpaperId); //得到要考试的卷子
    SubjectInfoVo getSubjet(HttpSession session, int testNumber); //根据卷子去拿题
    void destroyExam(HttpSession session); //用于考试结束时的销毁
}
