package sei.tk.service.dao.model;

public class TkQuetype {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_quetype.quetype_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private Short quetypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tk_quetype.quetype_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    private String quetypeName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_quetype.quetype_id
     *
     * @return the value of tk_quetype.quetype_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public Short getQuetypeId() {
        return quetypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_quetype.quetype_id
     *
     * @param quetypeId the value for tk_quetype.quetype_id
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setQuetypeId(Short quetypeId) {
        this.quetypeId = quetypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tk_quetype.quetype_name
     *
     * @return the value of tk_quetype.quetype_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public String getQuetypeName() {
        return quetypeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tk_quetype.quetype_name
     *
     * @param quetypeName the value for tk_quetype.quetype_name
     *
     * @mbggenerated Wed Mar 23 12:38:48 CST 2016
     */
    public void setQuetypeName(String quetypeName) {
        this.quetypeName = quetypeName;
    }
}