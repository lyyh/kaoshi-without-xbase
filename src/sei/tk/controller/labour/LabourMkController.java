package sei.tk.controller.labour;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.labour.*;
import sei.tk.service.rengong.RenGongService;
import sei.tk.util.HasValueUtil;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.annotation.NeedLogin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 人工制卷Controller
 * Created by Administrator on 2016/3/12 0012.
 */
@Controller
@RequestMapping(value = "/labour")
public class LabourMkController extends TkBaseController{
    @Resource
    LabourSeek labourSeek;
    @Resource
    LabourInsert labourInsert;
    @Resource
    LabourUpdate labourUpdate;
    @Resource
    LabourDelete labourDelete;
    @Resource
    RenGongService renGongService;
    /**
     * 显示所有试卷信息（出卷人，试卷id，出卷时间）
     * @return
     */
    @RequestMapping(value = "showTId")
    @ResponseBody
    public List<Map<String,Object>> showTestpaperId(){
        return labourSeek.seekTestpaperId();
    }

    /**
     * 根据testpaperId查找试卷信息和题目信息
     * @param testpaperId
     * @return
     */
    @RequestMapping(value = "showT")
    @ResponseBody
    public Map<String,Object> showTestpaper(Long testpaperId){
        return labourSeek.seekTestpaper(testpaperId);
    }


    /**
     *生成人工试卷
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/createPaper")
    @ResponseBody
    @NeedLogin(TkConfig.ROLE_TEACHER)
    public Object insertLabourPaper(HttpSession session,String courseName,String duration,String semesterTerm,
                                    String questionAndScore,String mkpaperType,String score,String note){
        SessionPassport sessionPassport=(SessionPassport)session.getAttribute("sessionPassport");
        //判断制卷内容是否完整
        HasValueUtil.hasValue(courseName,duration,semesterTerm,questionAndScore,mkpaperType,score);
        TkMkpaper tkMkpaper = new TkMkpaper();
        //将课程名称改成课程id
        tkMkpaper.setCourseId(renGongService.getTkCourse(courseName).getCourseId());
        tkMkpaper.setMkpaperExtime(Short.parseShort(duration));
        tkMkpaper.setPpassportId(sessionPassport.getPassportId());
        tkMkpaper.setMkpaperScore(Short.parseShort(score));
        tkMkpaper.setMkpaperTerm(semesterTerm);
        tkMkpaper.setPaperName(note);
        //判断制卷类型(1:人工制卷;2:机器制卷)
        if(mkpaperType.equals("1")){
            tkMkpaper.setMkpaperType(true);
        }else{
            tkMkpaper.setMkpaperType(false);
        }
        //将题目信息字符串转化为JSON数组
        JSONArray jsonArray = JSON.parseArray(questionAndScore);
        //默认给正式考试制卷
        if(labourInsert.insertTestSubject(jsonArray,labourInsert.insertTkTestpaper(tkMkpaper,labourInsert
                .insertTkMkpaper(tkMkpaper),false))){
            return LittleUtil.constructResponse(TkConfig.SUCCESS,"试卷生成成功!",null);
        }
        return LittleUtil.constructResponse(TkConfig.INVALID_ACTION,"试卷生成失败!",null);
    }

    /**
     * 删除人工试卷
     * @param testpaperId
     * @return
     */
    @RequestMapping(value = "deleteL")
    @ResponseBody
    public String deleteLabourPaper(Long testpaperId){
        Long mkpaperId = labourSeek.seekMkpaperId(testpaperId);
          if(labourDelete.deleteTestsubject(testpaperId)&&labourDelete.deleteTestpaper(testpaperId) &&
                  labourDelete.deleteMkpaper(mkpaperId)){
              return "success";
          }
        return "failure";
    }

    /**
     * 修改人工试卷
     * @param testpaperId
     * @param testJson
     * @param subjectJson
     * @return
     */
    @RequestMapping(value = "updateL")
    @ResponseBody
    public String updateLabourPaper(Long testpaperId,String testJson,String subjectJson){
        JSONObject jsonObject = JSON.parseObject(testJson);
        JSONArray jsonArray = JSON.parseArray(subjectJson);
        if(labourUpdate.updateMkpaper(jsonObject,labourUpdate.updateTestpaper(testpaperId,jsonObject))){
            labourUpdate.updateTestsubject(testpaperId,jsonArray);
            return "success";
        }
        return "failure";
    }
}
