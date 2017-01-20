package com.nixmash.wp.migrator.db.local.model;


import com.nixmash.wp.migrator.enums.PostDisplayType;
import com.nixmash.wp.migrator.enums.PostType;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
@Access(value = FIELD)
@NamedQueries({
        @NamedQuery(name = "Post.getByPostIds",
                query = "SELECT p FROM LocalPost p " +
                        "WHERE p.postId IN :postIds ORDER BY p.postDate DESC"),
})
public class LocalPost implements Serializable {

    private static final long serialVersionUID = 3533657789336113957L;

    public static final int MAX_POST_TITLE_LENGTH = 200;
    public static final int MAX_POST_NAME_LENGTH = 200;
    public static final int MIN_POST_CONTENT_LENGTH = 20;
    private static final String NA = "NA";

    // region properties

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_title", nullable = false, length = MAX_POST_TITLE_LENGTH)
    private String postTitle;

    @Column(name = "post_name", nullable = false, length = MAX_POST_NAME_LENGTH)
    private String postName;

    @Column(name = "post_link")
    private String postLink;

    @Column(name = "post_image", length = MAX_POST_NAME_LENGTH)
    private String postImage;

    @Column(name = "post_date", nullable = false)
    private ZonedDateTime postDate;

    @Column(name = "post_modified", nullable = false)
    private ZonedDateTime postModified;

    @Column(name = "post_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "display_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PostDisplayType displayType;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = true;

    @Column(name = "post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "post_source", length = 50)
    private String postSource = "NA";

    @Column(name = "click_count", nullable = false)
    private int clickCount = 0;

    @Column(name = "likes_count", nullable = false)
    private int likesCount = 0;

    @Column(name = "value_rating", nullable = false)
    private int valueRating = 0;

    @Version
    @Column(name = "version", nullable = false, insertable = true, updatable = true)
    private int version = 0;

    @Column(name = "wp_post_id", nullable = false)
    private Long wpPostId = 0L;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag_ids",
            joinColumns = @JoinColumn(name = "post_id",
                    referencedColumnName = "post_id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "tag_id",
                    nullable = false))
    public Set<LocalTag> tags;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_category_ids",
            joinColumns = @JoinColumn(name = "post_id",
                    referencedColumnName = "post_id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id",
                    referencedColumnName = "category_id",
                    nullable = false))
    public Set<LocalCategory> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    public LocalUser author;

    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser author) {
        this.author = author;
    }

    // endregion

    // region Transient properties

    @Transient
    public boolean isOwner = false;

    // endregion

    // region method properties

    public boolean isNew() {
        return (this.postId == null);
    }

    public String getAuthorFullname() {
        return author.getFirstName() + " " +  author.getLastName();
    }


    // endregion

    //region Getter Setters

    public boolean getIsOwner() {
        return isOwner;
    }
    public void setIsOwner(boolean owner) {
        isOwner = owner;
    }

    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostName() {
        return postName;
    }
    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostLink() {
        return postLink;
    }
    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    @CreatedDate
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    public ZonedDateTime getPostDate() {
        return postDate;
    }
    public void setPostDate(ZonedDateTime postDate) {
        this.postDate = postDate;
    }

    @LastModifiedDate
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    public ZonedDateTime getPostModified() {
        return postModified;
    }
    public void setPostModified(ZonedDateTime postModified) {
        this.postModified = postModified;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public PostDisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(PostDisplayType displayType) {
        this.displayType = displayType;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostSource() {
        return postSource;
    }

    public void setPostSource(String postSource) {
        this.postSource = postSource;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getValueRating() {
        return valueRating;
    }

    public void setValueRating(int valueRating) {
        this.valueRating = valueRating;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<LocalTag> getTags() {
        return tags;
    }

    public void setTags(Set<LocalTag> tags) {
        this.tags = tags;
    }

    public Set<LocalCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<LocalCategory> categories) {
        this.categories = categories;
    }

    public Long getWpPostId() {
        return wpPostId;
    }

    public void setWpPostId(Long wpPostId) {
        this.wpPostId = wpPostId;
    }

    //endregion

    public void update(String postTitle, String postContent, Boolean isPublished, PostDisplayType displayType) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isPublished = isPublished;
        this.displayType = displayType;
    }

    public void updateLikes(int likeIncrement) {
        this.likesCount = this.likesCount + likeIncrement;
    }

    @Override
    public String toString() {
        return "LocalPost{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", postTitle='" + postTitle + '\'' +
                ", postName='" + postName + '\'' +
                ", postDate=" + postDate +
                ", postModified=" + postModified +
                ", postType='" + postType + '\'' +
                ", displayType='" + displayType + '\'' +
                ", isPublished=" + isPublished +
                ", postContent='" + postContent + '\'' +
                ", postSource='" + postSource + '\'' +
                ", wpPostId =" + wpPostId+
                '}';
    }

    public static Builder getBuilder(Long userId, String postTitle, String postName, ZonedDateTime postDate, ZonedDateTime postModified, Long wpPostId ) {
        return new LocalPost.Builder(userId, postTitle, postName, postDate, postModified, wpPostId);
    }

    public static class Builder {

        private LocalPost built;

        public Builder(Long userId, String postTitle, String postName, ZonedDateTime postDate, ZonedDateTime postModified, Long wpPostId) {
            built = new LocalPost();
            built.userId = userId;
            built.postTitle = postTitle;
            built.postName = postName;
            built.postLink = NA;
            built.postContent = NA;
            built.postType = PostType.POST;
            built.displayType = PostDisplayType.POST;
            built.postSource = NA;
            built.postDate = postDate;
            built.postModified = postModified;
            built.wpPostId = wpPostId;
        }

        public Builder isPublished(Boolean isPublished) {
            built.setIsPublished(isPublished);
            return this;
        }

        public Builder postSource(String postSource) {
            built.postSource = postSource;
            return this;
        }

        public Builder postId(long postId) {
            built.postId = postId;
            return this;
        }

        public Builder postImage(String postImage) {
            if (StringUtils.isEmpty(postImage))
                postImage = null;
            built.postImage = postImage;
            return this;
        }

        public Builder tags(Set<LocalTag> tags) {
            built.tags= tags;
            return this;
        }

        public LocalPost build() {
            return built;
        }
    }
}
