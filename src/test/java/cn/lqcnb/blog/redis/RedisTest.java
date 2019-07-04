package cn.lqcnb.blog.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

//    @Autowired
//    private RedisTemplate<String,Object> template;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("k1","spring boot redis");
        System.out.println(redisTemplate.opsForValue().get("k1")+"---");
    }
    public void test1(){
        redisTemplate.opsForValue().set("k1","spring boot redis");
        System.out.println(redisTemplate.opsForValue().get("k1")+"---");
//        redisTemplate.opsForHash().pu
    }
}
