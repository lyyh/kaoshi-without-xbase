package sei.tk.util;

import java.util.List;

/**
 * 用于EasyUI的datagrid
 * Created by Heyiyong on 14-12-5.
 */
public class Page {
    private List rows;//页面数据
    private int total;//数据总数

    public Page(List rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
