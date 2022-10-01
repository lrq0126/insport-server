package com.dwgj.mlxydedicatedline.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 暂时弃用
 */
@Configuration
public class RedisConfigNew {

//    @Autowired
//    private RedisProperties properties;
//
//    @Bean
//    public JedisPool getJedisPool(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
//        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
//        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
//        JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(),(int) properties.getTimeout().toMillis(), properties.getPassword(), properties.getDatabase());
//        return pool;
//    }

}
