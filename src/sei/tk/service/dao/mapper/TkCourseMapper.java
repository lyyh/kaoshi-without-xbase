package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkCourseExample;

import java.util.List;

public interface TkCourseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int countByExample(TkCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByExample(TkCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByPrimaryKey(Short courseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insert(TkCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insertSelective(TkCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    List<TkCourse> selectByExample(TkCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    TkCourse selectByPrimaryKey(Short courseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExampleSelective(@Param("record") TkCourse record, @Param("example") TkCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExample(@Param("record") TkCourse record, @Param("example") TkCourseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKeySelective(TkCourse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_course
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKey(TkCourse record);
    /**
     * 得到所有课程
     * @return
     */
    List<TkCourse> selectAllCourse();
}