package cn.lqcnb.blog.api.controller;

import cn.lqcnb.blog.api.entity.ArticleType;
import cn.lqcnb.blog.api.service.ArticleTypeService;
import cn.lqcnb.blog.common.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "栏目管理")
public class ArticleTypeController {

    @Autowired
    ArticleTypeService articleTypeService;


    @ApiOperation(value = "通过会员id获取文章类型",notes = "通过会员id获取文章类型")
    @ApiImplicitParam(name = "memberID",value = "会员id",paramType = "query",required = true)
    @GetMapping("/getArticleTypeList")
    public R getArticleTypeList(int memberID){
        try {
            List<ArticleType> articleTypeList = articleTypeService.getArticleTypeList(memberID);
            return R.ok(articleTypeList);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


    @ApiOperation(value = "获取分页数据",notes = "获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" ,value = "页码",paramType="query",required = true),
            @ApiImplicitParam(name = "pageSize" ,value = "数量",paramType="query",required = true)
    })
    @GetMapping("/getColumnByPage")
    public R getColumnByPage(Integer pageNum,Integer pageSize){
        List<ArticleType> columnByPage = articleTypeService.getColumnByPage(null, pageNum, pageSize);
        System.out.println(columnByPage);
        if (columnByPage.size()!=0){
            return R.ok(columnByPage);
        }
        return R.error("没有获取到数据");
    }


    @ApiOperation(value = "添加栏目",notes = "添加栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" ,value = "栏目名称",paramType="query",required = true),
            @ApiImplicitParam(name = "memberID" ,value = "会员id",paramType="query",required = true)
    })
    @GetMapping("/addArticleType")
    public R addArticleType(String name,Integer memberID){
        try {
            if(articleTypeService.addArticleType(new ArticleType(name,memberID, DateUtils.parseDate(new Date().toLocaleString(),"yyyy-MM-dd HH:mm:ss")))){
                return R.ok("添加栏目成功");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.error("添加栏目失败");
    }


    @ApiOperation(value = "删除栏目",notes = "删除栏目")
    @ApiImplicitParam(name = "id",value = "栏目id",paramType = "query",required = true)
    @GetMapping("/deleteColumn")
    public R deleteArticleType(int id){
        if (articleTypeService.deleteArticleType(id)){
            return R.ok("删除栏目成功");
        }
        return R.error("删除栏目失败");
    }


    @ApiOperation(value = "修改栏目",notes = "修改栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" ,value = "栏目名称",paramType="query",required = true),
            @ApiImplicitParam(name = "id",value = "栏目id",paramType = "query",required = true)
    })
    @GetMapping("/updateColumn")
    public R updateArticleType(String name,Integer id){
        try {
            if (articleTypeService.update(new ArticleType(id,name, DateUtils.parseDate(new Date().toLocaleString(),"yyyy-MM-dd HH:mm:ss")))){
                return R.ok("修改栏目成功");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.error("修改栏目失败");
    }


    @ApiOperation(value = "获取栏目信息",notes = "获取栏目信息")
    @ApiImplicitParam(name = "id",value = "栏目id",paramType = "query",required = true)
    @GetMapping("/getColumnById")
    public R getArticleById(int id){
        ArticleType article = articleTypeService.getArticle(id);
        if (article!=null){
            return R.ok(article);
        }
        return R.error("获取数据失败");
    }

}
