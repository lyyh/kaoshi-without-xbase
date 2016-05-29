package sei.tk.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.course.Course;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.vo.course.CourseVo;
import sei.tk.util.Page;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2016/5/29.
 */
@Controller
public class CourseController {

    @Resource
    Course course;

    @ResponseBody
    @RequestMapping("/courseList")
    public Page CourseList(Integer page, Integer rows, CourseVo courseVo){

      return  course.CourseList(page,rows,courseVo);

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
