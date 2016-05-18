package sei.tk.service.test;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by liuruijie on 2016/3/12.
 */
public interface TimeService {
    Long getOddTime(HttpSession session); //获取考试剩余时间
    void setStart(HttpSession session); //记录考试开始时间
}
