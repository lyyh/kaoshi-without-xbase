package sei.tk.controller.paper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.vo.paper.PaperInfo;
import sei.tk.service.dao.model.vo.paper.PaperUp;
import sei.tk.service.dao.model.vo.passport.TeacherInfoVo;
import sei.tk.service.paper.PaperService;
import sei.tk.service.passportO.UserInfoService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by ywl on 2016/4/13.
 * 试卷列表管理
 */

@Controller
@RequestMapping("/paper")
public class PaperController extends TkBaseController{
    @Resource
    PaperService paperService;

    @Resource
    UserInfoService userInfoService;

    @RequestMapping("/deletepaper.do")
    @ResponseBody
    /**
     * 删除试卷
     */
    public boolean deletepaper(long testpaperId){

        return paperService.deletePaper(testpaperId);
    }
    @RequestMapping("/deletepapers.do")
    @ResponseBody
    /**
     * 批量删除试卷
     */
    public int deletepaper(Long[] testpaperIds){

        return paperService.deletePaperBatch(testpaperIds);
    }

    @RequestMapping("/selectallpaper.do")
    @ResponseBody
    /**
     * 得到试卷的制卷信息和相关信息
     */
    public Object selectallpaper(PaperInfo paperInfo, Integer page, Integer rows, HttpSession session){  //这里 分页
        TeacherInfoVo teacherInfoVo=null;
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        teacherInfoVo=userInfoService.getInfoTeacher(sessionPassport.getPassportId());
         paperInfo.setPpassportId(teacherInfoVo.getPassportId());

        return LittleUtil.constructResponse(TkConfig.SUCCESS,"",paperService.selectAllpaper(paperInfo,page,rows));
    }

    @RequestMapping("/selectpaperbyId.do")
    @ResponseBody
    /**
     * 得到试卷的制卷信息和相关信息
     */
    public PaperInfo selectpaperbyId(long testpaperId){

        return paperService.selectpaperinfobyId(testpaperId);
    }

    @RequestMapping("/updatepaper.do")
    @ResponseBody
    /**
     * 修改试卷信息
     */
    public boolean   updatepaper(@RequestBody PaperUp paperUp){

    return paperService.updatepaper(paperUp.getTkMkpaper(),paperUp.getTkTestsubject(),paperUp.getTkTestpaper());
    }



}
