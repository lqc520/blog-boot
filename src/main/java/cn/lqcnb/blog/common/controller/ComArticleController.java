package cn.lqcnb.blog.common.controller;

import cn.lqcnb.blog.api.entity.Article;
import cn.lqcnb.blog.api.entity.Member;
import cn.lqcnb.blog.api.service.ArticleService;
import cn.lqcnb.blog.api.service.ArticleTypeService;

import cn.lqcnb.blog.api.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ComArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private HttpSession session;


    @GetMapping("/index.html")
    public String toIndex(Model model){
        List<Article> randArticle = articleService.getRandArticle(5);
        System.out.println(randArticle);
        model.addAttribute("randArticle",randArticle);
        return "index";
    }

    /**
     *
     * @param model tagList columnList
     * @return agList columnList to member_article_add
     */
    @RequestMapping("toAddArticle")
    public String toAddArticle(Model model){
        Member member = (Member) session.getAttribute("member");
        if(member!=null){
            try {
                model.addAttribute("tagList",tagService.getTagListByMemberId(member.getId()));
                model.addAttribute("columnList",articleTypeService.getArticleTypeList(member.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member_article_add";
        }
        return "login";
    }


    @GetMapping("toMemberArticle")
    public String getArticleListBymID(Integer memberID,Integer pageNum,Integer pageSize,Model model){
        if(pageNum==null||pageNum<=0){
            pageNum=1;
        }
        if(pageSize==null||pageSize<=4){
            pageSize=4;
        }
        PageInfo<Article> page = articleService.findPage(new Article(memberID), pageNum, pageSize);
        if (pageNum>=page.getPages()&&page.getPages()>1){
            pageNum=page.getPages();
        }
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pageSize",pageSize);
        if(page.getList().size()!=0){
            model.addAttribute("articleList",page.getList());
           return "member_article";
        }
        return "member_article";//后期404
    }


    @GetMapping("/toDetail")
    public String toDetail(Integer id,Model model){
        Map article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        return "detail";
    }

    @GetMapping("/toUpdate")
    public String toUpdate(Integer id,Model model) {
        Member member = (Member) session.getAttribute("member");
        Map article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        if (member != null) {
            try {
                model.addAttribute("tagList", tagService.getTagListByMemberId(member.getId()));
                model.addAttribute("columnList", articleTypeService.getArticleTypeList(member.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member_article_update";
        }
        return "member_article_update";
    }

//    @GetMapping("/getArticles")
//    public String getArticles(Integer mid,Model model){
//        List<Map> articles = articleService.getArticles(mid);
//        model.addAttribute("articles",articles);
//        return "index";
//    }


}
