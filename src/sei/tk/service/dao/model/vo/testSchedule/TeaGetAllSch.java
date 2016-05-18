package sei.tk.service.dao.model.vo.testSchedule;


import java.util.Date;

/**
 * Created by 风中男子 on 2016-05-04.
 */
public class TeaGetAllSch { //用于dao层查询当前老师的安排
    private Integer start;
    private Integer row;

    private Long createPpassportId;

    public Long getCreatePpassportId() {
        return createPpassportId;
    }

    public void setCreatePpassportId(Long createPpassportId) {
        this.createPpassportId = createPpassportId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

}
