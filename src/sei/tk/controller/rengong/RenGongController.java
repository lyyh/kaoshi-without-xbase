package sei.tk.controller.rengong;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.rengong.RenGongService;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by lenovo on 2016/4/8.
 */

@Controller
public class RenGongController {

      @Resource
      RenGongService renGongService;



    //根据科目id得到 知识点列表
     @ResponseBody
     @RequestMapping("/rg_knpoints")
    public List<TkKnopoint>  rg_knpoints(Short courseId){
         List<TkKnopoint>  aa= renGongService.getKnopointList(courseId);
           return aa;

     }


    //根据人工制卷规则得到 题库的列表
    @ResponseBody
    @RequestMapping("/rg_getByKpoint")
    public List<TkSubjectWithBLOBs> getByKpoint( Short courseId,@RequestParam(required = false) Byte chapterId , Long knopointId, Byte levelId, Short quetypeId){
                     knopointId=knopointId==0?null:knopointId;
                    levelId=levelId==0?null:levelId;
        List<TkSubjectWithBLOBs> list= renGongService.getByKpoint(courseId,chapterId,knopointId,levelId,quetypeId);

        return list;
    }
     //得到科目的列表
    @ResponseBody
    @RequestMapping("/rg_getCourseList")
    public  List<TkCourse>  getCourseList(){
        return renGongService.getCourseList();
    }



     //根据科目姓名得到科目
    @ResponseBody
    @RequestMapping("/rg_getCourseByName")
    public TkCourse getCourse(String courseName){
          return  renGongService.getTkCourse(courseName);
    }

    @ResponseBody
    @RequestMapping("/mtr")
    public String getMtr(HttpSession session){
        SessionPassport sessionPassport=(SessionPassport)session.getAttribute("sessionPassport");
          if(sessionPassport==null){
              return "未命名";
          }
        return sessionPassport.getUserName();
    }



}
