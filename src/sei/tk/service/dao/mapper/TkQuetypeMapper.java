package sei.tk.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import sei.tk.service.dao.model.TkQuetype;
import sei.tk.service.dao.model.TkQuetypeExample;

import java.util.List;

public interface TkQuetypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int countByExample(TkQuetypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByExample(TkQuetypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int deleteByPrimaryKey(Short quetypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insert(TkQuetype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int insertSelective(TkQuetype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    List<TkQuetype> selectByExample(TkQuetypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    TkQuetype selectByPrimaryKey(Short quetypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExampleSelective(@Param("record") TkQuetype record, @Param("example") TkQuetypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByExample(@Param("record") TkQuetype record, @Param("example") TkQuetypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKeySelective(TkQuetype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_quetype
     *
     * @mbggenerated Wed Mar 23 08:59:41 CST 2016
     */
    int updateByPrimaryKey(TkQuetype record);
}