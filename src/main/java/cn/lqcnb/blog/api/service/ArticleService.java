package cn.lqcnb.blog.api.service;

import cn.lqcnb.blog.api.entity.Article;
import cn.lqcnb.blog.api.mapper.ArticleMapper;
import cn.lqcnb.blog.common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleService extends AbstractService<Article> {
    @Autowired(required = false)
    ArticleMapper articleMapper;
    public boolean addArticle(Article article){
        return this.add(article);
    }

    public List<Map> getArticlesByMid(Integer mid){
        return articleMapper.getArticlesByMid(mid);
    }

    public List<Map> getArticles(){
        return articleMapper.getArticles();
    }

    public Map getArticleById(Integer id){
        return articleMapper.getArticleById(id);
    }

    public List<Map> getArticlesByTitle(String title){
        return articleMapper.getArticlesByTitle(title);
    }

    public List<Article> getRandArticle(int limit){
        return articleMapper.getRandArticle(limit);
    }



}
