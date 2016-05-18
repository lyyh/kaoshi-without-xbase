package sei.tk.service.dao.model.vo.testSchedule;

import java.util.Date;

/**
 * Created by ·çÖÐÄÐ×Ó on 2016-05-04.
 */
public class TeaGetSchId {
    private Date testStarttime;
    private Date testEndtime;
    private Long testpaperId;

    public Date getTestStarttime() {
        return testStarttime;
    }

    public void setTestStarttime(Date testStarttime) {
        this.testStarttime = testStarttime;
    }

    public Date getTestEndtime() {
        return testEndtime;
    }

    public void setTestEndtime(Date testEndtime) {
        this.testEndtime = testEndtime;
    }

    public Long getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Long testpaperId) {
        this.testpaperId = testpaperId;
    }
}
