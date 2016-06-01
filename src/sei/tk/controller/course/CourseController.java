package sei.tk.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.course.Course;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.vo.course.CourseVo;
import sei.tk.util.LittleUtil;
import sei.tk.util.Page;
import sei.tk.util.TkConfig;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2016/5/29.
 */
@Controller
public class CourseController extends TkBaseController {

    @Resource
    Course course;

    @ResponseBody
    @RequestMapping("/courseList")
    public Object CourseList(Integer page, Integer rows, CourseVo courseVo){

      return LittleUtil.constructResponse(TkConfig.SUCCESS,"",course.CourseList(page,rows,courseVo));

    }

     @ResponseBody
      @RequestMapping("/getSingle")
    public CourseVo getSingle(CourseVo courseVo){

         return course.getTkCourse(courseVo.getCourseId());

    }

     @ResponseBody
    @RequestMapping("/coursedelete")
    public String delete(TkCourse courseVo){

        if(course.delete(courseVo))
           return "success";
        return  "删除失败";
    }
    @ResponseBody
    @RequestMapping("/courseadd")
        public String  courseadd(TkCourse tkCourse){

            if(course.add(tkCourse)) {
                return "success";
            }

            return  "新增失败";
        }



}
