package sei.tk.util;

import sei.tk.util.exception.TKException;

/**
 * 判断值是否
 * Created by liuyanhao on 26/4/16.
 */
public class HasValueUtil {
    public static void hasValue(String...args){
        for(String str:args){
            if(str==null||str.equals("")){
                throw new TKException(TkConfig.INVALID_ACTION,"请完善制卷内容!");
            }
        }
    }

}
