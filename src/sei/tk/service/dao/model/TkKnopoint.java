package sei.tk.service.dao.model;

public class TkKnopoint {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_knopoint.knopoint_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private Long knopointId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_knopoint.course_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private Short courseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_knopoint.knopoint_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private String knopointName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_knopoint.chapter_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private Byte chapterId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_knopoint.knopoint_id
     *
     * @return the value of tk_knopoint.knopoint_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public Long getKnopointId() {
        return knopointId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_knopoint.knopoint_id
     *
     * @param knopointId the value for tk_knopoint.knopoint_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setKnopointId(Long knopointId) {
        this.knopointId = knopointId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_knopoint.course_id
     *
     * @return the value of tk_knopoint.course_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public Short getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_knopoint.course_id
     *
     * @param courseId the value for tk_knopoint.course_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setCourseId(Short courseId) {
        this.courseId = courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_knopoint.knopoint_name
     *
     * @return the value of tk_knopoint.knopoint_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public String getKnopointName() {
        return knopointName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_knopoint.knopoint_name
     *
     * @param knopointName the value for tk_knopoint.knopoint_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setKnopointName(String knopointName) {
        this.knopointName = knopointName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_knopoint.chapter_id
     *
     * @return the value of tk_knopoint.chapter_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public Byte getChapterId() {
        return chapterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_knopoint.chapter_id
     *
     * @param chapterId the value for tk_knopoint.chapter_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setChapterId(Byte chapterId) {
        this.chapterId = chapterId;
    }
}