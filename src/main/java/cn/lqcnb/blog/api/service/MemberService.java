package cn.lqcnb.blog.api.service;

import cn.lqcnb.blog.api.entity.Member;
import cn.lqcnb.blog.api.mapper.MemberMapper;
import cn.lqcnb.blog.common.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.UUID;

@Service
public class MemberService{
    @Autowired(required = false)
    private MemberMapper memberMapper;

    public boolean addMember(Member member) {
        return memberMapper.insertSelective(member) > 0;
    }

    public Member memberLogin(Member member) {
        return memberMapper.selectOne(member);
    }

    public boolean updateMember(Member member){
        return memberMapper.updateByPrimaryKeySelective(member)>0;
    }

    public Member getMemberById(int id){
        return memberMapper.selectByPrimaryKey(id);
    }

//    public boolean saveFile(MultipartFile file, String path) {
//        // 判断文件是否为空
//        if (!file.isEmpty()) {
//            try {
//                File filepath = new File(path);
//                if (!filepath.exists())
//                    filepath.mkdirs();
//                // 文件保存路径
//                String fileName = file.getOriginalFilename();
//                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//                String uid= UUID.randomUUID().toString().replace("-", "");
//                String savePath = path + System.getProperty("file.separator")+uid+"."+suffix;
//                // 转存文件
//                file.transferTo(new File(savePath));
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

}
