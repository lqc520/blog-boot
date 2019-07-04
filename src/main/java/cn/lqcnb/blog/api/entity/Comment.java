package cn.lqcnb.blog.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Comment {
    @Id
    private Integer id;

    private String content;

    @Column(name = "blog_id")
    private Integer blogId;

    @Column(name = "form_member_id")
    private Integer formMemberId;

    @Column(name = "to_menber_id")
    private Integer toMenberId;

    @Column(name = "crate_time")
    private Date crateTime;

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

    /**
     * @return blog_id
     */
    public Integer getBlogId() {
        return blogId;
    }

    /**
     * @param blogId
     */
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    /**
     * @return form_member_id
     */
    public Integer getFormMemberId() {
        return formMemberId;
    }

    /**
     * @param formMemberId
     */
    public void setFormMemberId(Integer formMemberId) {
        this.formMemberId = formMemberId;
    }

    /**
     * @return to_menber_id
     */
    public Integer getToMenberId() {
        return toMenberId;
    }

    /**
     * @param toMenberId
     */
    public void setToMenberId(Integer toMenberId) {
        this.toMenberId = toMenberId;
    }

    /**
     * @return crate_time
     */
    public Date getCrateTime() {
        return crateTime;
    }

    /**
     * @param crateTime
     */
    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }
}