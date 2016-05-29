package sei.tk.service.course.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.course.Course;
import sei.tk.service.dao.mapper.TkCourseMapper;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.dao.model.vo.course.CourseVo;
import sei.tk.service.rengong.RenGongService;
import sei.tk.service.subject.KnowledgePointService;
import sei.tk.util.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2016/5/29.
 */
@Service
public class CourseServiceImpl implements Course {

    @Resource
    TkCourseMapper tkCourseMapper;

    @Resource
    RenGongService renGongService;

    @Override
    public CourseVo getTkCourse(Short id) {

          TkCourse tkCourse=tkCourseMapper.selectByPrimaryKey(id);
               List<TkKnopoint> list=  renGongService.getKnopointList(tkCourse.getCourseId());
         CourseVo courseVo=new CourseVo();
         courseVo.setCourseId(tkCourse.getCourseId());
         courseVo.setCourseName(tkCourse.getCourseName());
         StringBuffer stringBuffer=new StringBuffer();
         for(TkKnopoint tkKnopoint:list){
             stringBuffer.append(tkKnopoint.getKnopointName()+",");
         }
         stringBuffer.substring(0,stringBuffer.lastIndexOf(",")-1);
        courseVo.setTkKnopoint(stringBuffer.toString());
        return  courseVo;
    }

    @Override
    public Page CourseList(Integer currentPage, Integer rows, CourseVo courseVo) {
          Integer total=tkCourseMapper.countList(courseVo.getCourseName());
         courseVo.setStart((currentPage-1)*rows);
         courseVo.setAmount(rows);
        List<CourseVo> list=    tkCourseMapper.selectByExamplelist(courseVo);
             for(CourseVo temp:list){
                 List<TkKnopoint> list1=renGongService.getKnopointList(temp.getCourseId());
                 StringBuffer stringBuffer=new StringBuffer();
                 for(TkKnopoint tkKnopoint:list1){
                     stringBuffer.append(tkKnopoint.getKnopointName()+",");
                 }
                 stringBuffer.substring(0,stringBuffer.lastIndexOf(",")-1);
                 temp.setTkKnopoint(stringBuffer.toString());
             }
         Page page=new Page(list,total);
        return page;
    }

    @Override
    public boolean delete(TkCourse tkCourse) {

        int i=tkCourseMapper.deleteByPrimaryKey(tkCourse.getCourseId());
         if(i!=0){
             return  true;
         }
        return false;
    }
}
