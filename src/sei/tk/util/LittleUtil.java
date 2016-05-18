package sei.tk.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by liuruijie on 2016/4/5.
 */
public class LittleUtil {

    /**
     * 将返回值统一格式
     * @param code 错误码
     * @param msg 错误信息
     * @param data 数据
     * @return 返回的json对象
     */
    public static JSONObject constructResponse(int code, String msg, Object data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }

    public static boolean isAllBlank(String[] arr){
        boolean flag=true;
        for(String s:arr){
            if(!s.equals("")){
                flag=false;
                break;
            }
        }
        return flag;
    }

    public static String filterImg(String html){
        StringBuffer stringBuffer=new StringBuffer("");
        int i=0;
        boolean flag=false;
        for(;;){
            if(flag)stringBuffer.append("[图片]");
            int s=html.indexOf("<img",i);
            if(s==-1)break;
            flag=true;
            int e=html.indexOf(">",s);
            stringBuffer.append(html.substring(i,s));
            i=e+1;
        }
        stringBuffer.append(html.substring(i,html.length()));
        return stringBuffer.toString();
    }

    public static String removeOuterTagP(String html){
        if(html.matches("^<p>.*</p>$")){
            return html.substring(3,html.length()-4);
        }else{
            return html;
        }
    }
}
