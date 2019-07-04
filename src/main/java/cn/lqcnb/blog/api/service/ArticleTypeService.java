package cn.lqcnb.blog.api.service;

import cn.lqcnb.blog.api.entity.ArticleType;
import cn.lqcnb.blog.common.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeService extends AbstractService<ArticleType> {

    public List<ArticleType> getArticleTypeList(Integer memberID) throws Exception {
        if (memberID==null){
            throw new Exception("没有获取到memberID");
        }
        List<ArticleType> list = this.findList(new ArticleType(memberID));
        if (list.size()==0){
            throw new Exception("没有找到数据 请会员先插入数据");
        }
        return list;
    }

    public boolean addArticleType(ArticleType articleType){
        return add(articleType);
    }

    public boolean deleteArticleType(Integer id){
        return deleteById(id);
    }

    public ArticleType getArticle(Integer id){
        return getById(id);
    }

    public List<ArticleType> getColumnByPage(ArticleType articleType,int pageNum,int pageSize){
       return findList(articleType,pageNum,pageSize);
    }

}
