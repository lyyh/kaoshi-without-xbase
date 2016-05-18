package sei.tk.service.exam;

import com.alibaba.fastjson.JSON;
import sei.tk.util.JedisUtil;
import sei.tk.util.exception.TKException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuruijie on 2016/4/7.
 */
public class ExamMapForRedis {
    public void put(long stuId,String key,Object value){
        JedisUtil.hset("" + stuId, key, JSON.toJSONString(value));
    }

    public String get(long stuId,String key){
        return JedisUtil.hget(""+stuId,key);
    }

    public void remove(long stuId){
        JedisUtil.del("" + stuId);
    }
}
