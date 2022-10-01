package com.dwgj.mlxydedicatedline.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 暂时弃用
 */
@Component
@Slf4j
public class RedisUtil {

//    @Autowired
//    private JedisPool jedisPool;
//
//    // ..... 和下面的那些操作差不多 jedisPool.getResource();可以获取redis操作对象
//    /**
//     * 通过key获取储存在redis中的value
//     * @param key
//     * @param indexdb 选择redis库 0-15
//     * @return 成功返回value 失败返回null
//     */
//    public String get(String key,int indexdb) {
//        Jedis jedis = null;
//        String value = null;
//        try {
//            jedis = jedisPool.getResource();
//            jedis.select(indexdb);
//            value = jedis.get(key);
//            log.info(value);
//        } catch (Exception e) {
//
//            log.error(e.getMessage());
////        } finally {
////            return Resource(jedisPool, jedis);
//        }
//        return value;
//    }
//
//    public static void main(String[] args) {
//        new RedisUtil().get("name",0);
//    }

}
