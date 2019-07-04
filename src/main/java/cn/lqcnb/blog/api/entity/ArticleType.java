package cn.lqcnb.blog.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "article_type")
public class ArticleType {
    @Id
    private Integer id;

    private String name;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "create_time")
    private Date createTime;

    public ArticleType(Integer memberId) {
        this.memberId = memberId;
    }

    public ArticleType(String name, Integer memberId, Date createTime) {
        this.name = name;
        this.memberId = memberId;
        this.createTime = createTime;
    }

    public ArticleType(String name) {
        this.name = name;
    }

    public ArticleType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArticleType(Integer id, String name, Date createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ArticleType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", memberId=" + memberId +
                ", createTime=" + createTime +
                '}';
    }

    public ArticleType() {
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return member_id
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}