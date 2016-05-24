package sei.tk.util.exception;

/**
 * Created by liuruijie on 2016/4/9.
 */
public class TKException extends RuntimeException{
    private String errorCode;//对应TkConfig中的错误码

    public TKException(String errorCode,String exceptionMessage){
        super(exceptionMessage);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
