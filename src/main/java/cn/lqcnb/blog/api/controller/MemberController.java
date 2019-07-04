package cn.lqcnb.blog.api.controller;

import cn.lqcnb.blog.common.entity.R;
import cn.lqcnb.blog.common.utils.AliyunSMSUtil;
import cn.lqcnb.blog.common.utils.TencentSMSUtils;
import cn.lqcnb.blog.api.entity.Member;
import cn.lqcnb.blog.api.service.MemberService;
import cn.lqcnb.blog.common.utils.UploadUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "会员管理")
@RestController
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    HttpSession session;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    HttpServletRequest request;


    @ApiOperation(value = "添加会员",notes = "添加会员")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "member" ,value = "会员对象",paramType="query",dataType = "Member")
            @ApiImplicitParam(name = "mobile" ,value = "手机号",paramType="query"),
            @ApiImplicitParam(name = "password" ,value = "密码",paramType="query"),
            @ApiImplicitParam(name = "code" ,value = "验证码",paramType="query")
    })
    @GetMapping("addMember")
    public R addMember(String mobile,String password,@RequestParam("code") String SMSCode){
//        String code= (String) session.getAttribute("code");
        String code = this.getCode(mobile);
        if(code.equals(SMSCode)){
            if(memberService.addMember(new Member(mobile,password))){
                return R.ok("注册成功");
            }
            return R.error("添加会员信息失败");
        }
        return R.error("验证码错误");
    }


    @ApiOperation(value = "获取腾讯云短信验证码",notes = "获取腾讯云短信验证码")
    @ApiImplicitParam(name = "mobile" ,value = "手机号",paramType="query",required = true)
    @GetMapping("getTencentSMSCode")
    public R getTencentSMSCode(String mobile){
        String code = TencentSMSUtils.getSMSCode(mobile);
        if (code!=null){
//            session.setAttribute("code",code);
            setCodeTime(mobile,code,60);
            return R.ok();
        }
        return R.error("获取短信验证码错误");
    }


    @ApiOperation(value = "获取阿里云短信验证码",notes = "获取阿里云短信验证码")
    @ApiImplicitParam(name = "mobile" ,value = "手机号",paramType="query",required = true)
    @GetMapping("getAliyunSMSCode")
    public R getAliyunSMSCod(String mobile ){
        String code = AliyunSMSUtil.sendCode(mobile);
        setCodeTime(mobile,code,60);
        System.out.println(mobile+":"+code);
        if (code!=null){
            session.setAttribute("code",code);
            return R.ok("获取验证码成功",code);
        }
        return R.error("获取短信验证码错误");
    }


    @ApiOperation(value = "会员登录",notes = "会员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile" ,value = "手机号",paramType="query",required = true),
            @ApiImplicitParam(name = "password" ,value = "密码",paramType="query",required = true)
    })
    @GetMapping("memberLogin")
    public R Login(String mobile, String password){
        Member member = memberService.memberLogin(new Member(mobile, password));
        System.out.println(member);
        if(member!=null){
            session.setAttribute("member",member);
            return R.ok("登录成功",member);
        }
        return R.error("用户名或者密码错误");
    }


    @PostMapping("updateMember")
    @ApiOperation(value = "修改会员信息",notes = "修改会员信息")
    @ApiImplicitParam(name = "member" ,value = "手机号",paramType="query",required = true,dataType = "Member")
    public R updateMember(Member member){
        System.out.println(member);
        if(memberService.updateMember(member)){
            Member memberById = memberService.getMemberById(member.getId());
            session.setAttribute("member",memberService.getMemberById(member.getId()));
            return R.ok("修改会员信息成功",memberById);
        }
        return R.error("修改会员信息失败");
    }


    @PostMapping("updateMemberPassword")
    @ApiOperation(value = "修改会员密码",notes = "修改会员密码")
    @ApiImplicitParam(name = "member" ,value = "手机号",paramType="query",required = true,dataType = "Member")
    public R updateMemberPassword(Integer id,String password,String newpassword){

        if(password.equals(((Member)session.getAttribute("member")).getPassword())){
            if(memberService.updateMember(new Member(id,newpassword))){
                session.setAttribute("member",memberService.getMemberById(id));
                return R.ok("修改会员密码成功");
            }
            return R.error("修改会员密码失败");
        }
        return R.error("修改会员密码失败");
    }

    @PostMapping("addMemberImg")
    @ApiOperation(value = "添加会员头像",notes = "添加会员头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "会员id",paramType="query",required = true),
            @ApiImplicitParam(name = "file" ,value = "头像",paramType="query",dataType="file")
    })
    public R addMemberImg(Integer id,@RequestParam("file") MultipartFile file){
 if(file!=null&&file.getSize()>0){
            if(file.getOriginalFilename().endsWith("jpg")||file.getOriginalFilename().endsWith("png")){
             try {
                 String path = UploadUtil.upload(file,"avatar");
                 Member member=new Member();
                 member.setId(id);
                 member.setAvatar(path);
                 if(memberService.updateMember(member)){
                     Member memberById = memberService.getMemberById(id);
                     session.setAttribute("member",memberById);
                     return R.ok("上传头像成功",path);
                  }

             } catch (Exception e) {
                    e.printStackTrace();
                 return R.error(e.getMessage());
             }
            }
            return R.error("文件格式错误不是.jpg或.png结尾");
        }
        return R.error("无文件上传");
    }
    /**
     *
     * @param mobile 手机号
     * @param code 验证码
     * @param time 验证码有效期
     */
    public void setCodeTime(String mobile,String code,long time){
        redisTemplate.opsForValue().set("mobile-code:"+mobile,code);
        redisTemplate.expire("mobile-code:"+mobile,time, TimeUnit.SECONDS);
    }

    /**
     *
     * @param mobile 手机号
     * @return 验证码
     */
    public String getCode(String mobile){
        return redisTemplate.opsForValue().get("mobile-code:"+mobile);
    }



}
