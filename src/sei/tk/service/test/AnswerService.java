package sei.tk.service.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/3/12.
 */
public interface AnswerService {
    void setTempAnswer(HttpSession session, Integer number, String stuAnswer, String[] stuBlankAnswer); //记录临时答案
    Integer submitTestPaper(HttpServletRequest request); //提交试卷并计算分数
}
