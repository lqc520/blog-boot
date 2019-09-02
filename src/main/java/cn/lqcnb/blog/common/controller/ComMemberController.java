package cn.lqcnb.blog.common.controller;

import cn.lqcnb.blog.api.controller.MemberController;
import cn.lqcnb.blog.api.entity.Member;
import cn.lqcnb.blog.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComMemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("to_user_update/{id}")
    public String toUpdate(@PathVariable String id, Model model){
        Member user = memberService.getMemberById(Integer.parseInt(id));
        model.addAttribute("user",user);
        return "/admin/user_update";
    }
}
