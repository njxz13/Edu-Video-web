package com.example.Edu_Video_web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Map;

@SpringBootTest
class EduVideoWebApplicationTests {

    private Jedis jedis;

    @BeforeEach
    void setUp() {
        jedis = new Jedis("localhost", 8989);
        if(jedis != null) {
            System.out.println("jedis连接成功!");
        }else {
            System.out.println("jedis连接失败!");
        }
    }

    @Test
    void testString() {
        String result = jedis.set("name", "pg13");
        System.out.println(result);
        String name = jedis.get("name");
        System.out.println(name);
    }

    @Test
    void testHash() {
        //插入哈希值
        jedis.hset("user:1", "name", "sga");
        jedis.hset("user:1", "age", "19");
        //获取哈希值
        Map<String, String> map = jedis.hgetAll(("user:1"));
        System.out.println(map);
    }

    @AfterEach
    void tearDown() {
        if(jedis != null) {
            jedis.close();
            System.out.println("jedis对象已销毁");
        }
    }

}