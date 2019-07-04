package cn.lqcnb.blog.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest

public class MemberControllerTest {

    @Autowired
    HttpServletRequest request;

    @Test
    public void addMember() {
    }

    @Test
    public void getTencentSMSCode() {
    }

    @Test
    public void getAliyunSMSCod() {
    }

    @Test
    public void login() {
    }

    @Test
    public void t() {
        String realPath = request.getServletContext().getRealPath("upload/avatar");
        System.out.println(realPath);
    }
}