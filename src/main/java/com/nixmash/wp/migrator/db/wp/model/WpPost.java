package com.nixmash.wp.migrator.db.wp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by daveburke on 1/18/17.
 */
@Entity
@Table(name = "wp_posts")
public class WpPost implements Serializable {

    private static final long serialVersionUID = -1504614709210489277L;

    private Long postId;
    private Timestamp postDate;
    private String postContent;
    private Long userId;
    private String postStatus;
    private String postName;
    private Timestamp postModified;
    private String postType;
    private String postTitle;

    @Id
    @Column(name = "ID")
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "post_date")
    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    @Basic
    @Column(name = "post_content", columnDefinition = "LONGTEXT")
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    @Basic
    @Column(name = "post_author")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "post_status")
    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    @Basic
    @Column(name = "post_name")
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Basic
    @Column(name = "post_modified")
    public Timestamp getPostModified() {
        return postModified;
    }

    public void setPostModified(Timestamp postModified) {
        this.postModified = postModified;
    }

    @Basic
    @Column(name = "post_type")
    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }


    @Basic
    @Column(name = "post_title", columnDefinition = "TEXT")
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Override
    public String toString() {
        return "WpPost{" +
                ", postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", userId=" + userId +
                ", postDate=" + postDate +
                ", postContent='" + postContent + '\'' +
                ", postStatus='" + postStatus + '\'' +
                ", postName='" + postName + '\'' +
                ", postModified=" + postModified +
                ", postType='" + postType + '\'' +
                '}';
    }
}
