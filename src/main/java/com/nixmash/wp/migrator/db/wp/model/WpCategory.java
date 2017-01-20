package com.nixmash.wp.migrator.db.wp.model;

import javax.persistence.*;

/**
 * Created by daveburke on 1/20/17.
 */
@Entity
@Table(name = "v_nixmash_wpcategories")
public class WpCategory {
    private Long wpCategoryId;
    private String categoryValue;

    @Id
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
}
