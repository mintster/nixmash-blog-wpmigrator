package com.nixmash.wp.migrator.db.wp.model;

import javax.persistence.*;
import java.io.Serializable;

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
                                @ColumnResult(name = "wp_post_id", type = Long.class)
                        }
                )
        }
)
public class WpPostCategory implements Serializable {

    private static final long serialVersionUID = 6284662776872664114L;

    private Long wpCategoryId;
    private Long wpPostId;

    public WpPostCategory(Long wpCategoryId,  Long wpPostId) {
        this.wpCategoryId = wpCategoryId;
        this.wpPostId = wpPostId;
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
    @Column(name = "wp_post_id")
    public Long getWpPostId() {
        return wpPostId;
    }

    public void setWpPostId(Long wpPostId) {
        this.wpPostId = wpPostId;
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
