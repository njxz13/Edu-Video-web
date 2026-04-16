package com.example.Edu_Video_web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@SpringBootApplication
public class TestJedisPool {

    private static final JedisPool jedisPool;

    static {
        //创建连接池对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        //设置最长等待时间 单位：ms
        jedisPoolConfig.setMaxWait(Duration.ofSeconds(200));

        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 8989, 3000);
    }
    
    public static Jedis getJedis() {
         return jedisPool.getResource();
    }
}
