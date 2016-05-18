package sei.tk.util;

import java.util.Random;

/**
 * Created by liuruijie on 2016/3/15.
 * 随机打乱顺序相关方法
 */
public class RandomUtil {
    public static Object[] unOrder(Object[] datas){//随机打乱传进来的数组
        if(datas.length<=0)return datas;
        Random random=new Random(System.currentTimeMillis());

        Object[] unOrderData=new Object[datas.length];
        int key=random.nextInt(datas.length);
        for(int i=0;i<datas.length;i++){
            if(datas[key]==null){
                key=random.nextInt(datas.length);
                i--;
                continue;
            }
            unOrderData[i]=datas[key];
            datas[key]=null;
        }
        for(int i=0;i<unOrderData.length;i++){
            datas[i]=unOrderData[i];
        }
        return datas;
    }
}
