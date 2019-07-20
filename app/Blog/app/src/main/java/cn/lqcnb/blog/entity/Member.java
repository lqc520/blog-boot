package cn.lqcnb.blog.entity;

public class Member {

    private Integer id;

    private String mobile;

    private String password;

    private String name;

    private String nickname;

    private String sex;

    private String email;

    private String blogName;

    /**
     * 头像
     */
    private String avatar;


    public Member(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Member(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Member() {
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", blogName='" + blogName + '\'' +
                ", avatar='" + avatar + '\'' +
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
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return blog_name
     */
    public String getBlogName() {
        return blogName;
    }

    /**
     * @param blogName
     */
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}