package com.nixmash.wp.migrator.db.local.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by daveburke on 1/22/17.
 */
@Entity
@Table(name = "post_category_ids")
public class LocalPostCategory {
    private Long postCategoryId;
    private Long postId;
    private Long categoryId;

    @Id
    @Column(name = "post_category_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getPostCategoryId() {
        return postCategoryId;
    }

    public void setPostCategoryId(Long postCategoryId) {
        this.postCategoryId = postCategoryId;
    }

    @Basic
    @Column(name = "post_id")
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalPostCategory() {
    }

    public LocalPostCategory(Long postId, Long categoryId) {
        this.postId = postId;
        this.categoryId = categoryId;
    }

    public static Builder getWpBuilder(Long postId, Long categoryId) {
        return new Builder(postId, categoryId);
    }

    public static class Builder {

        private LocalPostCategory built;

        public Builder(Long postId, Long categoryId) {
            built = new LocalPostCategory();
            built.categoryId = categoryId;
            built.postId = postId;
        }

        public LocalPostCategory build() {
            return built;
        }
    }

}
