package cn.lqcnb.blog.api.entity;

import javax.persistence.*;

public class Tag {
    @Id
    private Integer id;

    private String name;

    @Column(name = "member_id")
    private Integer memberId;

    public Tag(Integer memberId) {
        this.memberId = memberId;
    }

    public Tag(String name, Integer memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", memberId=" + memberId +
                '}';
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
}