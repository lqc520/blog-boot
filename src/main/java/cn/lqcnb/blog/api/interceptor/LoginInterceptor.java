package cn.lqcnb.blog.api.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if (request.getSession().getAttribute("member")==null){
           System.out.println("LoginInterceptor.preHandle");
           request.getRequestDispatcher("login.html").forward(request,response);
           return false;
       }
       return true;
    }
}
