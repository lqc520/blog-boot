package cn.lqcnb.blog.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Visit {
    @Id
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "visit_id")
    private Integer visitId;

    /**
     * 头像
     */
    @Column(name = "visit_avatar")
    private String visitAvatar;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return article_id
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * @return visit_id
     */
    public Integer getVisitId() {
        return visitId;
    }

    /**
     * @param visitId
     */
    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    /**
     * 获取头像
     *
     * @return visit_avatar - 头像
     */
    public String getVisitAvatar() {
        return visitAvatar;
    }

    /**
     * 设置头像
     *
     * @param visitAvatar 头像
     */
    public void setVisitAvatar(String visitAvatar) {
        this.visitAvatar = visitAvatar;
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