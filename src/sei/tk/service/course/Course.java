package sei.tk.service.course;

import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.vo.course.CourseVo;
import sei.tk.util.Page;

import java.util.List;

/**
 * Created by lenovo on 2016/5/29.
 */
public interface Course {
    public CourseVo getTkCourse(Short id);
    public Page CourseList(Integer currentPage, Integer rows, CourseVo courseVo);
    public boolean delete(TkCourse tkCourse);
    public boolean add(TkCourse tkCourse);

}
