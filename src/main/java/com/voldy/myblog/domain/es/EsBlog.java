package com.voldy.myblog.domain.es;

import com.voldy.myblog.domain.Blog;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/9
 **/
@Document(indexName = "blog", type = "blog")
@XmlRootElement // MediaType 转为 XML
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id  // 主键
    private String id;
    @Field(type = FieldType.Keyword)
    private Long blogId; // Blog 的 id

    private String title;

    private String summary;

    private String content;

    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Keyword)
    private String icon;
    @Field(type = FieldType.Long)
    private Timestamp createTime;
    @Field(type = FieldType.Integer)
    private Integer readings = 0; // 访问量、阅读量
    @Field(type = FieldType.Integer)
    private Integer comments = 0;  // 评论量
    @Field(type = FieldType.Integer)
    private Integer votes = 0;  // 点赞量
    @Field(type = FieldType.Text, fielddata = true)
    private String tags;  // 标签

    protected EsBlog() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    public EsBlog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public EsBlog(Long blogId, String title, String summary, String content, String username, String icon, Timestamp createTime,
                  Integer readings, Integer comments, Integer votes, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.icon = icon;
        this.createTime = createTime;
        this.readings = readings;
        this.comments = comments;
        this.votes = votes;
        this.tags = tags;
    }

    public EsBlog(Blog blog){
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.icon = blog.getUser().getIcon();
        this.createTime = blog.getCreateTime();
        this.readings = blog.getReadings();
        this.comments = blog.getComments();
        this.votes = blog.getVotes();
        this.tags = blog.getTags();
    }

    public void update(Blog blog){
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.icon = blog.getUser().getIcon();
        this.createTime = blog.getCreateTime();
        this.readings = blog.getReadings();
        this.comments = blog.getComments();
        this.votes = blog.getVotes();
        this.tags = blog.getTags();
    }


    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getReadings() {
        return readings;
    }

    public void setReadings(Integer readings) {
        this.readings = readings;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "blogId=" + blogId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", icon='" + icon + '\'' +
                ", createTime=" + createTime +
                ", readings=" + readings +
                ", comments=" + comments +
                ", votes=" + votes +
                ", tags='" + tags + '\'' +
                '}';
    }
}