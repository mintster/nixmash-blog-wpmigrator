package com.nixmash.wp.migrator.db.local.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by daveburke on 1/23/17.
 */
@Entity
@Table(name = "post_tag_ids")
public class LocalPostTag {
    private Long postTagId;
    private Long postId;
    private Long tagId;

    @Id
    @Column(name = "post_tag_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getPostTagId() {
        return postTagId;
    }

    public void setPostTagId(Long postTagId) {
        this.postTagId = postTagId;
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
    @Column(name = "tag_id")
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public LocalPostTag() {
    }

    public LocalPostTag(Long postId, Long tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public static Builder getWpBuilder(Long postId, Long tagId) {
        return new Builder(postId, tagId);
    }

    public static class Builder {

        private LocalPostTag built;

        public Builder(Long postId, Long tagId) {
            built = new LocalPostTag();
            built.tagId = tagId;
            built.postId = postId;
        }

        public LocalPostTag build() {
            return built;
        }
    }

}
