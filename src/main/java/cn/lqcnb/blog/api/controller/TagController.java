package cn.lqcnb.blog.api.controller;

import cn.lqcnb.blog.api.entity.Tag;
import cn.lqcnb.blog.api.service.TagService;
import cn.lqcnb.blog.common.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@Api(tags = "标签管理")
public class TagController {
    @Autowired
    TagService tagService;


    /**
     *
     * @param memberID 会员id
     * @param model
     * @return tagList to member_tag
     */
    @GetMapping("getTag")
    public String getTagListById(Integer memberID, Model model){
        try {
            List<Tag> tagList = tagService.getTagListByMemberId(memberID);
            model.addAttribute("tagList",tagList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "member_tag";
    }


    @GetMapping("addTag")
    @ResponseBody
    @ApiOperation(value = "添加标签",notes = "添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "会员标签id",paramType = "query",required = true),
            @ApiImplicitParam(name = "name",value = "标签名字",paramType = "query",required = true),
    })
    public R addTag(Integer id,String name){
        tagService.addTag(new Tag(name,id));
//        if(tagService.addTag(new Tag(name,id))){
//            R.ok("添加标签成功");
//        }
//       return R.error("添加标签失败");
        return R.ok("添加标签成功");
    }


    @GetMapping("deleteTag")
    @ResponseBody
    @ApiOperation(value = "删除标签",notes = "删除标签")
    @ApiImplicitParam(name = "id",value = "标签id",paramType = "query",required = true)
    public R deleteTag(Integer id){
        if(tagService.deleteTag(id)){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }


    @GetMapping("getTagListByMemberId")
    @ResponseBody
    @ApiOperation(value = "通过会员id获取文章标签",notes = "通过会员id获取文章标签")
    @ApiImplicitParam(name = "memberID",value = "会员id",paramType = "query",required = true)
    public R getTagListByMemberId(Integer memberID){
        try {
            List<Tag> tagList = tagService.getTagListByMemberId(memberID);
            return R.ok(tagList);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }



}
