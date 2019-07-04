package cn.lqcnb.blog.common.controller;

import cn.lqcnb.blog.api.entity.Article;
import cn.lqcnb.blog.api.entity.ArticleType;
import cn.lqcnb.blog.api.service.ArticleTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ComColumnController {
    @Autowired
    ArticleTypeService articleTypeService;

    @GetMapping("toColumn")
    public String toColumn(Integer memberID, Integer pageNum, Integer pageSize, Model model){
        if(pageNum==null||pageNum<=0){
            pageNum=1;
        }
        if(pageSize==null||pageSize<=4){
            pageSize=4;
        }
        PageInfo<ArticleType> page = articleTypeService.findPage(new ArticleType(memberID), pageNum, pageSize);
        if (pageNum>=page.getPages()&&page.getPages()>=2){
            pageNum=page.getPages();
        }
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pageSize",pageSize);
        if(page.getList().size()!=0){
            model.addAttribute("columnList",page.getList());
            return "member_column";
        }
        return "member_column";//后期404
    }

    @GetMapping("getColumn")
    public String getArticleTypeList(int memberID, Model model){
        try {
            List<ArticleType> articleTypeList = articleTypeService.getArticleTypeList(memberID);
            System.out.println(articleTypeList);
            model.addAttribute("columnList",articleTypeList);
            return "member_column";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "member_column"; //后期改成404界面

    }

    @GetMapping("/getArticleById")
    public String getArticleById(int id,Model model){
        ArticleType article = articleTypeService.getArticle(id);
        if (article!=null){
            model.addAttribute("column",article);
            return "member_column_update";
        }
        return "member_column_update";
    }
}
