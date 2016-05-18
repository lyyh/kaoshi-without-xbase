package sei.tk.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.util.LittleUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

/**
 * Created by liuruijie on 2016/4/26.
 * 处理异常的类，需要处理异常的Controller直接继承这个类
 */
public class TkBaseController {

    /**
     * 处理Controller抛出的异常
     * @param e 异常实例
     * @return Controller层的返回值
     */
    @ExceptionHandler
    @ResponseBody
    public Object expHandler(Exception e){
        if(e instanceof TKException){
            TKException tkEx= (TKException) e;
            return LittleUtil.constructResponse(tkEx.getErrorCode(), tkEx.getMessage(), null);
        }else{
            e.printStackTrace();
            return LittleUtil.constructResponse(TkConfig.SYSTEM_ERROR,"系统错误",null);
        }
    }
}
