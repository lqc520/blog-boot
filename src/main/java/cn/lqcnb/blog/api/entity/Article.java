package cn.lqcnb.blog.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Article {
    @Id
    private Integer id;

    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 文章分类ID
     */
    @Column(name = "article_type_id")
    private Integer articleTypeId;

    @Column(name = "tag_id")
    private Integer tagId;

    private String title;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", articleTypeId=" + articleTypeId +
                ", tagId=" + tagId +
                ", title='" + title + '\'' +
                ", attachment='" + attachment + '\'' +
                ", visit='" + visit + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                '}';
    }

    /**
     * 附件
     */
    private String attachment;

    private String visit;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String content;

    public Article(Integer memberId) {
        this.memberId = memberId;
    }

    public Article(Integer id, String attachment) {
        this.id = id;
        this.attachment = attachment;
    }

    public Article() {
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
     * 获取文章分类ID
     *
     * @return article_type_id - 文章分类ID
     */
    public Integer getArticleTypeId() {
        return articleTypeId;
    }

    /**
     * 设置文章分类ID
     *
     * @param articleTypeId 文章分类ID
     */
    public void setArticleTypeId(Integer articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    /**
     * @return tag_id
     */
    public Integer getTagId() {
        return tagId;
    }

    /**
     * @param tagId
     */
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取附件
     *
     * @return attachment - 附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置附件
     *
     * @param attachment 附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * @return visit
     */
    public String getVisit() {
        return visit;
    }

    /**
     * @param visit
     */
    public void setVisit(String visit) {
        this.visit = visit;
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

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}