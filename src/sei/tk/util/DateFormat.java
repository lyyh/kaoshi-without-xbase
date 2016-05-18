package sei.tk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuruijie on 2016/2/21.
 * 时间与字符串相互转换
 */
public class DateFormat {
    public static Date StringToDate(String date,String model){//字符串转时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(model);
        Date date1=null;
        try {
            date1=simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String DateToString(Date date,String model){//时间转字符串
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(model);
        return simpleDateFormat.format(date);
    }
}
