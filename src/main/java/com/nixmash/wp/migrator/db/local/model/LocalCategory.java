package com.nixmash.wp.migrator.db.local.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "categories")
public class LocalCategory implements Serializable {

    private static final Long serialVersionUID = -5531381747015731447L;

    private Long categoryId;
    private Long wpCategoryId;
    private String categoryValue;
    private Set<LocalPost> posts;
    private Integer categoryCount = 0;

    public LocalCategory() {
    }

    public LocalCategory(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public LocalCategory(Long categoryId, String categoryValue) {
        this.categoryId = categoryId;
        this.categoryValue = categoryValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_id", nullable = false)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_value", nullable = false, length = 50)
    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    @Basic
    @Column(name = "wp_category_id", nullable = false)
    public Long getWpCategoryId() {
        return wpCategoryId;
    }

    public void setWpCategoryId(Long wpCategoryId) {
        this.wpCategoryId = wpCategoryId;
    }


    @ManyToMany(mappedBy = "categories")
    public Set<LocalPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<LocalPost> posts) {
        this.posts = posts;
    }

    @Transient
    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    @Override
    public String toString() {
        return getCategoryValue();
    }

    public void update(final String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public static Builder getBuilder(Long categoryId, String categoryValue) {
        return new Builder(categoryId, categoryValue);
    }

    public static Builder getWpBuilder(String categoryValue, Long wpCategoryId) {
        return new Builder(categoryValue, wpCategoryId);
    }

    public static class Builder {

        private LocalCategory built;

        public Builder(Long categoryId, String categoryValue) {
            built = new LocalCategory();
            built.categoryId = categoryId;
            built.categoryValue = categoryValue;
        }

        public Builder(String categoryValue, Long wpCategoryId) {
            built = new LocalCategory();
            built.categoryId = 0L;
            built.categoryValue = categoryValue;
            built.wpCategoryId = wpCategoryId;
        }

        public LocalCategory build() {
            return built;
        }
    }

}
