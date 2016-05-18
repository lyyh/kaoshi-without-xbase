package sei.tk.service.dao.model.vo.testSchedule;

import java.util.List;

/**
 * Created by ·çÖÐÄÐ×Ó on 2016-05-02.
 */
public class PageTestInfo {
    private List<TestInfo> rows;
    private Integer total;

    public PageTestInfo() {
    }

    public PageTestInfo(List<TestInfo> rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public List<TestInfo> getRows() {
        return rows;
    }

    public void setRows(List<TestInfo> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
