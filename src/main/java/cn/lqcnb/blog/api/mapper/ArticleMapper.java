package cn.lqcnb.blog.api.mapper;

import cn.lqcnb.blog.api.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends Mapper<Article> {
    public List<Map> getArticlesByMid(Integer mid);
    public List<Map> getArticles();
    public Map getArticleById(Integer id);
    public List<Map> getArticlesByTitle(String title);

}