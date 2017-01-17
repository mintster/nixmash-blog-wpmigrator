package com.nixmash.wp.migrator.db.local.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "categories")
public class LocalCategory implements Serializable {

    private static final long serialVersionUID = -5531381747015731447L;

    private long categoryId;
    private long wpCategoryId;
    private String categoryValue;
    private Set<LocalPost> posts;
    private int categoryCount = 0;

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
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
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
    public long getWpCategoryId() {
        return wpCategoryId;
    }

    public void setWpCategoryId(long wpCategoryId) {
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
    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
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

    public static class Builder {

        private LocalCategory built;

        public Builder(Long categoryId, String categoryValue) {
            built = new LocalCategory();
            built.categoryId = categoryId;
            built.categoryValue = categoryValue;
        }

        public LocalCategory build() {
            return built;
        }
    }

}
