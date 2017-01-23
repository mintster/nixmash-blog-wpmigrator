package com.nixmash.wp.migrator.db.wp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by daveburke on 1/23/17.
 */
@Entity
@Table(name = "v_nixmash_wpposttags")
@SqlResultSetMapping(name = "AllWpPostTags",
        classes = {
                @ConstructorResult(
                        targetClass = WpPostTag.class,
                        columns = {
                                @ColumnResult(name = "wp_tag_id", type = Long.class),
                                @ColumnResult(name = "wp_post_id", type = Long.class)
                        }
                )
        }
)
public class WpPostTag  implements Serializable {

    private static final long serialVersionUID = 5983607315827790467L;

    private Long wpTagId;
    private Long wpPostId;
    private String id;

    public WpPostTag() {
    }


    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "wp_tag_id")
    public Long getWpTagId() {
        return wpTagId;
    }

    public void setWpTagId(Long wpTagId) {
        this.wpTagId = wpTagId;
    }

    @Basic
    @Column(name = "wp_post_id")
    public Long getWpPostId() {
        return wpPostId;
    }

    public void setWpPostId(Long wpPostId) {
        this.wpPostId = wpPostId;
    }

    public WpPostTag(Long wpTagId, Long wpPostId) {
        this.wpTagId = wpTagId;
        this.wpPostId = wpPostId;
    }
}
