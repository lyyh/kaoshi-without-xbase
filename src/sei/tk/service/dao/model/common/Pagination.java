package sei.tk.service.dao.model.common;

/**
 * Created by liuruijie on 2016/5/17.
 */
public class Pagination {
    protected Integer start;
    protected Integer rows;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
