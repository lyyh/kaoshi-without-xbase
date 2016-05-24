package sei.tk.controller.subject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.subject.KnowledgePointService;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by liuruijie on 2016/5/12.
 */
@Controller
@RequestMapping("knopoint")
public class KnowledgePoint extends TkBaseController{
    @Resource
    KnowledgePointService knowledgePointService;

    @RequestMapping("list")
    @ResponseBody
    public Object _list(HttpSession session,Integer page,Integer rows,TkKnopoint knopoint){
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,knowledgePointService.getAllKnowledgePointByPage(page, rows, knopoint));
    }

    @RequestMapping("show")
    @ResponseBody
    public Object _show(HttpSession session,Long knopointId){
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,knowledgePointService.getKnopointById(knopointId));
    }

    @RequestMapping("del")
    @ResponseBody
    public Object _del(HttpSession session,Long knopointId){
        knowledgePointService.deleteKnopoint(knopointId);
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,null);
    }

    @RequestMapping("save")
    @ResponseBody
    public Object _save(HttpSession session,TkKnopoint knopoint,String type){
        if("edit".equals(type)){
            knowledgePointService.updateKnopoint(knopoint);
        }else if("add".equals(type)){
            knowledgePointService.insertKnopoint(knopoint);
        }else{
            throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        }
        return LittleUtil.constructResponse(TkConfig.SUCCESS,null,null);
    }
}
