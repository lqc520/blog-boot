package cn.lqcnb.blog.entity;

import java.io.Serializable;

public class Article implements Serializable {
    private String special;
    private String create_time;
    private String author;
    private int id;
    private String tag;
    private String title;
    private String content;
    private String avatar;

    public Article(String special, String create_time, String author, int id, String tag, String title, String content,String avatar) {
        this.special = special;
        this.create_time = create_time;
        this.author = author;
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "special='" + special + '\'' +
                ", create_time='" + create_time + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
