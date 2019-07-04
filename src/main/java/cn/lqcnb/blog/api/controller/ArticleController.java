package cn.lqcnb.blog.api.controller;

import cn.lqcnb.blog.api.entity.Article;
import cn.lqcnb.blog.api.entity.Member;
import cn.lqcnb.blog.api.service.ArticleService;
import cn.lqcnb.blog.common.entity.R;
import cn.lqcnb.blog.common.utils.UploadUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    HttpSession session;

    @ApiIgnore
    @GetMapping("/addArticleJson")
    public void addArticleJson(){
        try {
            String articlelist = FileUtils.readFileToString(new File("E:\\Study\\Junior\\JavaEE\\JavaEE Program\\blog\\src\\main\\java\\cn\\lqcnb\\blog\\api\\controller\\article_list.json"), "UTF-8");
            List<Article> articles = JSONArray.parseObject(articlelist, new TypeReference<List<Article>>() {
            });
            for (Article article:articles){
                System.out.println(article);
                article.setId(null);
                articleService.addArticle(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("addArticle")
    @ApiOperation(value = "发布文章",notes = "发布文章")
    @ApiImplicitParam(name = "article" ,value = "文章内容",paramType = "query", dataType = "Article")
    public R addArticle(Article article){
        System.out.println(article);
        Member  member = (Member) session.getAttribute("member");
        if(member!=null||article.getMemberId()!=null){
            try {
                article.setMemberId(member.getId());
                article.setCreateTime(DateUtils.parseDate(new Date().toLocaleString(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(articleService.add(article)){
                return R.ok("发布文章成功");
            }
            return R.error("发布文章失败");
        }

        return R.error("发布文章失败 请先登录");
    }

    @PostMapping("UpdateArticle")
    @ApiOperation(value = "发布文章",notes = "发布文章")
    @ApiImplicitParam(name = "article" ,value = "文章内容",paramType = "query", dataType = "Article")
    public R UpdateArticle(Article article){
        System.out.println(article);
        Member  member = (Member) session.getAttribute("member");
        if(member!=null||article.getMemberId()==null){
            try {
                article.setMemberId(member.getId());
                article.setUpdateTime(DateUtils.parseDate(new Date().toLocaleString(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
                return R.error(e.getMessage());
            }
            if(articleService.update(article)){
                return R.ok("修改文章成功");
            }
            return R.error("修改文章失败");
        }
        return R.error("修改文章失败 请先登录");
    }

    @GetMapping("deleteArticle/{id}")
    @ApiOperation(value = "删除文章",notes = "删除文章")
    @ApiImplicitParam(name = "id" ,value = "文章id",paramType="path",required = true)
    public R deleteArticle(@PathVariable Integer id){
        if(articleService.deleteById(id)){
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }


    @PostMapping("AddAttachment")
    @ApiOperation(value = "上传附件",notes = "上传附件")
    @ApiImplicitParam(name = "file" ,value = "附件",paramType="query",dataType="file")
    public R AddAttachment(Integer id,@RequestParam("file_attachment") MultipartFile file){
        if(file!=null&&file.getSize()>0){
            String attachment = UploadUtil.upload(file, "attachment");
            if(attachment!=null)
            return  R.ok("上传附件成功",attachment);
        }
        return R.error("上传附件失败");
    }


    @GetMapping("getArticleListByMID")
    @ApiOperation(value = "获取用户文章",notes = "获取用户文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberMID" ,value = "会员用户id",paramType = "query",required = true),
            @ApiImplicitParam(name = "pageNum" ,value = "页号",paramType = "query"),
            @ApiImplicitParam(name = "pageSize" ,value = "数据数量",paramType = "query")
    })
    public R getArticleListBymID(Integer memberID,Integer pageNum,Integer pageSize){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=2;
        }
        List<Article> articleList = articleService.findList(new Article(memberID), pageNum, pageSize);
        if(articleList.size()==0){
            return R.error("获取用户文章数据失败");
        }
        return R.ok(articleList);
    }

    @GetMapping("getArticleByID")
    @ApiOperation(value = "获取文章内容",notes = "获取文章内容")
    @ApiImplicitParam(name = "id" ,value = "文章id",paramType="query",required =true)
    public Map getArticleByID(Integer id){
        Map article = articleService.getArticleById(id);
        if(article!=null){
            return article;
        }
        return null;
    }


    @ApiOperation(value = "获取用户文章列表",notes = "获取用户文章列表")
    @ApiImplicitParam(name = "mid" ,value = "会员id",paramType = "query")
    @GetMapping("/getArticlesByMid")
    public List<Map> getArticlesByMid(Integer mid){
        List<Map> articles = articleService.getArticlesByMid(mid);
        return articles;
    }


    @ApiOperation(value = "获取文章数据",notes = "获取文章数据")
    @GetMapping("/getArticles")
    public List<Map> getArticles(){
        List<Map> articles = articleService.getArticles();
        return articles;
    }

    @ApiOperation(value = "获取文章数据模糊查询",notes = "获取文章数据模糊查询")
    @ApiImplicitParam(name = "title" ,value = "标题",paramType = "query")
    @GetMapping("/getArticlesByTitle")
    public List<Map> getArticles(String title){
        List<Map> articles = articleService.getArticlesByTitle(title);
        return articles;
    }



//    @ApiOperation(value = "获取文章数据",notes = "获取文章数据")
//    @ApiImplicitParam(name = "id" ,value = "文章id",paramType = "query")
//    @GetMapping("/getArticleById")
//    public Map getArticleById(Integer id){
//        Map article = articleService.getArticleById(id);
//        return article;
//    }





}
