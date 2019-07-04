package cn.lqcnb.blog.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Api("通用api")
public class CommonController {


    @Autowired
    HttpSession session;
//    @RequestMapping("/*")
//    public String doForward(HttpServletRequest request){
//        String servletPath = request.getServletPath();
//        String url = servletPath.replace(".html", "");
//        return url;
//    }
    @RequestMapping({"member_column_add.html","member_tag_add.html","detail.html","member_article_update.html","register.html","login.html","index.html","member_info.html","member_password.html","member_column.html","member_index.html","member_article.html","member_article_list.html","member_article_add.html","member_article_add2.html","blog_article_detail.html","blog_index.html","common_nav.html"})
    public String doForward(HttpServletRequest request){
        String servletPath = request.getServletPath();
        String url = servletPath.replace(".html", "");
        return url;
    }

    @GetMapping("loginOut")
    @ApiOperation(value = "退出登录",notes = "退出登录")
    public String loginOut(){
        session.removeAttribute("member");
        return "login";
    }
//   @RequestMapping("login")
//    public String doLogin(){
//          return "login";
//      }
//    @RequestMapping("index")
//    public String doIndex(){
//        return "index";
//    }
//    @RequestMapping("register")
//    public String doRegister(){
//        return "register";
//    }


}
