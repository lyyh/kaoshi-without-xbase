package sei.tk.util.exception;

/**
 * Created by liuruijie on 2016/4/9.
 */
public class TKException extends RuntimeException{
    private int errorCode;//对应TkConfig中的错误码

    public TKException(int errorCode,String exceptionMessage){
        super(exceptionMessage);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
