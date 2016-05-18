package sei.tk.util;

import redis.clients.jedis.*;

import sei.system.service.aop.SpringContextUtil;

/**
 * Created by liuruijie on 2016/4/8.
 */
public class JedisUtil  {
    private static ShardedJedisPool shardedJedisPool;
    public static ShardedJedisPool getJedisPoole(){
        if(shardedJedisPool==null) {
            //获取连接池实例
            shardedJedisPool= (ShardedJedisPool) SpringContextUtil
                    .getApplicationContext().getBean("shardedJedisPool");
        }
        return shardedJedisPool;
    }
    public static void closeJedis(){
        shardedJedisPool.close();
    }
    public static Long hset(String key,String field,String value){//封装jedis的hset方法
        ShardedJedis shardedJedis=getJedisPoole().getResource();//获取链接
        Long hset = shardedJedis.hset(key, field, value);
        shardedJedis.close();//资源返还
        return hset;
    }
    public static String hget(String key,String field){
        ShardedJedis shardedJedis=getJedisPoole().getResource();
        String hget=shardedJedis.hget(key, field);
        shardedJedis.close();
        return hget;
    }
    public static Long del(String key){
        ShardedJedis shardedJedis=getJedisPoole().getResource();
        //if(shardedJedis.exists(key))throw new TKException("key不存在");
        Long del=shardedJedis.del(key);
        shardedJedis.close();
        return del;
    }
}
