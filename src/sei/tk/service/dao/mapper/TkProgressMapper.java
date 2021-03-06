package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.TkProgress;
import sei.tk.service.dao.model.TkProgressExample;

import java.util.List;

public interface TkProgressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int countByExample(TkProgressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByExample(TkProgressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByPrimaryKey(Long proUuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insert(TkProgress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insertSelective(TkProgress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    List<TkProgress> selectByExample(TkProgressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    TkProgress selectByPrimaryKey(Long proUuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExampleSelective(@Param("record") TkProgress record, @Param("example") TkProgressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExample(@Param("record") TkProgress record, @Param("example") TkProgressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKeySelective(TkProgress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_progress
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKey(TkProgress record);
}