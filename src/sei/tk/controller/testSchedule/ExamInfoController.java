package sei.tk.controller.testSchedule;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.testSchedule.PageTestInfo;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;
import sei.tk.service.testSchedule.ExamInfoService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 风中男子 on 2016-03-25.
 * 获取与当前用户相关的考试相关信息
 */
@Controller
@RequestMapping("/ExamInfo")
public class ExamInfoController extends TkBaseController {
    @Resource
    private ExamInfoService examInfoService;

    @ResponseBody
    @RequestMapping("/getAllPaperInfo")
    public JSONObject getAllPaperInfo(HttpSession session, Integer page, Integer rows) { //老师得到所有试卷（无论安排与否）
        Long sessionPassport = null;
            sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",examInfoService.getAllPaperInfo(sessionPassport,page,rows));
    }

    @ResponseBody
    @RequestMapping("/getPaperInfoDetails")
    public JSONObject getPaperInfoDetails(HttpSession session,Long testscheduleId,Long testpaperId) { //得到试卷属性细节
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",examInfoService.getPaperInfoDetails(sessionPassport,testscheduleId,testpaperId));
    }


    @ResponseBody
    @RequestMapping("/getAllExamInfo")
    public JSONObject getAllExamInfo(HttpSession session,Integer page,Integer rows) {  //老师得到所有已安排试卷
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",examInfoService.getAllExamInfo(sessionPassport,page,rows));
    }

    @ResponseBody
    @RequestMapping("/getFutureExamInfo")
    public JSONObject getFutureExamInfo(HttpSession session) {  //得到未考试卷，没有分数
        List<TestInfo> testInfoList = new ArrayList<>();
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        testInfoList=examInfoService.getFutureExamInfo(sessionPassport);
        PageTestInfo pageTestInfo=new PageTestInfo(testInfoList,testInfoList.size());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",pageTestInfo);
    }

    @ResponseBody
    @RequestMapping("/getValidExamInfo")
    public JSONObject getValidExamInfo(HttpSession session) {  //得到进行中的考卷，没有分数
        List<TestInfo> testInfoList = new ArrayList<>();
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        testInfoList=examInfoService.getValidExamInfo(sessionPassport);
        PageTestInfo pageTestInfo=new PageTestInfo(testInfoList,testInfoList.size());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",pageTestInfo);
    }

    @ResponseBody
    @RequestMapping("/getOverdueExamInfo")
    public JSONObject getOverdueExamInfo(HttpSession session) {    //得到过期考卷
        List<TestInfo> testInfoList = new ArrayList<>();
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        testInfoList=examInfoService.getOverdueExamInfo(sessionPassport);
        PageTestInfo pageTestInfo=new PageTestInfo(testInfoList,testInfoList.size());
        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",pageTestInfo);
    }

    @RequestMapping("/listMyExam")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_STUDENT)
    public Object listMyExam(HttpSession session){
        long passportId = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,examInfoService.getMyExam(passportId));
    }
}
