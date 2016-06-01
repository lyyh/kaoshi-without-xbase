package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.vo.course.CourseVo;

import java.util.List;

/**
 * Created by lenovo on 2016/5/29.
 */
public interface CourseInfoMapper {


    Integer countList(@Param("courseName")String courseName);

    List<CourseVo> selectByExamplelist(CourseVo courseVo);
}
