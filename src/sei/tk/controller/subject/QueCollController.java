package sei.tk.controller.subject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.subject.QueCollInfo;
import sei.tk.service.subject.QuecollService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by ywl on 2016/6/1.
 */
@Controller
@RequestMapping("quecoll")
public class QueCollController {
    @Resource
    QuecollService quecollService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Object selectAll(HttpSession session,QueCollInfo queCollInfo,Integer page, Integer rows ){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");
        queCollInfo.setStuId(sessionPassport.getPassportId());
        return LittleUtil.constructResponse(TkConfig.SUCCESS, "",quecollService.selectAllQuecoll(queCollInfo, page, rows));
    }
    @RequestMapping("selectbyid")
    @ResponseBody
    public QueCollInfo selectByid(Long  quecollId){
        return  quecollService.selectByid(quecollId);
    }

}
