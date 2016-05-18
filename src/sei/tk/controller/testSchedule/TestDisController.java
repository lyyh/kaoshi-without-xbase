package sei.tk.controller.testSchedule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.vo.passport.SessionPassport;
import sei.tk.service.dao.model.vo.testSchedule.Mkpaper;
import sei.tk.service.testSchedule.TestDisService;
import sei.tk.util.DateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by 风中男子 on 2016-03-26.
 * 按照试卷学期和科目分发试卷到相应选了课的学生
 */
@Controller
@RequestMapping("/TestDis")
public class TestDisController {
    @Resource
    private TestDisService testDisService;

    @ResponseBody
    @RequestMapping("/getMkpaperInfo") //根据当前老师id得到其所选课程的所有制卷信息
    public List<Mkpaper> getMkpaperInfo(HttpSession session) {
        return testDisService.getMkpaperInfo(((SessionPassport) session.getAttribute("sessionPassport")).getPpassportId());
    }

    @ResponseBody
    @RequestMapping("/")   //根据当前的老师id给学生分发试卷并设置相应信息，没有分数
    public int disTestpaper(HttpSession session,Long mkpaperId, String testStarttime, String testEndtime) {
        Date start= DateFormat.StringToDate(testStarttime, "yyyy-MM-dd hh:mm:ss");
        Date end=DateFormat.StringToDate(testEndtime,"yyyy-MM-dd hh:mm:ss");
        Long ppassportId=((SessionPassport) session.getAttribute("sessionTeacher")).getPpassportId();
        return testDisService.disTestpaper(ppassportId,mkpaperId,start,end);
    }
}
