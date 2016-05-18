package sei.tk.service.dao.model.vo.common;

/**
 * Created by liuruijie on 2016/3/25.
 * 用于后台返回的类
 */
public class ResultVo {
    private Object obj;
    private String errorMsg;

    public ResultVo(){
    }
    public ResultVo(String error){
        errorMsg=error;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
