package com.nixmash.wp.migrator.db.wp.model;

import javax.persistence.*;

/**
 * Created by daveburke on 1/22/17.
 */
@Entity
@Table(name = "v_nixmash_wppostcategories")
@SqlResultSetMapping(name = "AllWpPostCategories",
        classes = {
                @ConstructorResult(
                        targetClass = WpPostCategory.class,
                        columns = {
                                @ColumnResult(name = "wp_category_id", type = Long.class),
                                @ColumnResult(name = "wp_post_id", type = Long.class),
                                @ColumnResult(name = "category_id", type = Long.class),
                                @ColumnResult(name = "category_value"),
                                @ColumnResult(name = "post_title")
                        }
                )
        }
)
public class WpPostCategory {
    private Long wpCategoryId;
    private String categoryValue;
    private Long wpPostId;
    private String postTitle;
    private Long categoryId;

    public WpPostCategory(Long wpCategoryId,  Long wpPostId, Long categoryId, String categoryValue, String postTitle) {
        this.wpCategoryId = wpCategoryId;
        this.categoryValue = categoryValue;
        this.wpPostId = wpPostId;
        this.postTitle = postTitle;
        this.categoryId = categoryId;
    }

    public WpPostCategory() {
    }

    @Basic
    @Column(name = "wp_category_id")
    public Long getWpCategoryId() {
        return wpCategoryId;
    }

    public void setWpCategoryId(Long wpCategoryId) {
        this.wpCategoryId = wpCategoryId;
    }

    @Basic
    @Column(name = "category_value")
    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    @Basic
    @Column(name = "wp_post_id")
    public Long getWpPostId() {
        return wpPostId;
    }

    public void setWpPostId(Long wpPostId) {
        this.wpPostId = wpPostId;
    }

    @Basic
    @Column(name = "post_title", columnDefinition = "TEXT")
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Basic
    @Column(name = "category_id", columnDefinition = "INTEGER")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
