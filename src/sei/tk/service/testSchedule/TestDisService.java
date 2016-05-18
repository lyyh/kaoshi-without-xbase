package sei.tk.service.testSchedule;

import sei.tk.service.dao.model.vo.testSchedule.Mkpaper;

import java.util.Date;
import java.util.List;

/**
 * Created by 风中男子 on 2016-03-26.
 * 按照试卷学期和科目分发试卷到相应选了课的学生
 */
public interface TestDisService {
    List<Mkpaper> getMkpaperInfo(Long ppassportId);   //根据当前老师id得到相应课程的所有制卷信息
    Integer disTestpaper(Long ppassportId,Long mkpaperId,Date testStarttime,Date testEndtime);  //根据当前的老师id给学生分发试卷并设置相应信息
}
