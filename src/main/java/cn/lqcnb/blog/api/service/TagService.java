package cn.lqcnb.blog.api.service;

import cn.lqcnb.blog.api.entity.Tag;
import cn.lqcnb.blog.common.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends AbstractService<Tag> {
    public List<Tag> getTagListByMemberId(Integer MemberId) throws Exception {
        if (MemberId==null){
            throw new Exception("会员id为 null 无法找到数据");
        }
        List<Tag> list = this.findList(new Tag(MemberId));
        if(list.size()==0){
            throw new Exception("没有找到数据 请会员先插入数据");
        }
        return list;
    }

    public boolean deleteTag(Integer id) {
        return deleteById(id);
    }

    public boolean addTag(Tag tag){
       return this.add(tag);
    }
}
