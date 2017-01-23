package com.nixmash.wp.migrator.db.local.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tags")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getTagCloud",
                query = "select count(*) as `tagCount`, t.tag_value, t.tag_id from tags t " +
                        " inner join post_tag_ids pt on t.tag_id = pt.tag_id " +
                        " inner join posts p on pt.post_id = p.post_id " +
                        " where p.is_published = true " +
                        "group by t.tag_value order by t.tag_value;",
                resultClass = LocalTag.class)
})
public class LocalTag implements Serializable {

    private static final Long serialVersionUID = -5531381747015731447L;

    private Long tagId;
    private Long wpTagId;
    private String tagValue;
    private Set<LocalPost> posts;
    private int tagCount = 0;

    public LocalTag() {
    }

    public LocalTag(String tagValue) {
        this.tagValue = tagValue;
    }

    public LocalTag(Long tagId, String tagValue) {
        this.tagId = tagId;
        this.tagValue = tagValue;
    }

    public LocalTag(Long tagId, Long wpTagId, String tagValue) {
        this.tagId = tagId;
        this.wpTagId = wpTagId;
        this.tagValue = tagValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id", nullable = false)
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_value", nullable = false, length = 50)
    public String getTagValue() {
        return tagValue;
    }

    @Basic
    @Column(name = "wp_tag_id", nullable = false)
    public Long getWpTagId() {
        return wpTagId;
    }

    public void setWpTagId(Long wpTagId) {
        this.wpTagId = wpTagId;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @ManyToMany(mappedBy = "tags")
    public Set<LocalPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<LocalPost> posts) {
        this.posts = posts;
    }

    @Transient
    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    @Override
    public String toString() {
        return getTagValue();
    }

    public void update(final String tagValue) {
        this.tagValue = tagValue;
    }

    public static Builder getBuilder(Long tagId, String tagValue, Long wpTagId) {
        return new Builder(tagId, tagValue, wpTagId);
    }
    public static Builder getWpBuilder(String tagValue, Long wpTagId) {
        return new Builder(0L, tagValue, wpTagId);
    }

    public static class Builder {

        private LocalTag built;

        public Builder(Long tagId, String tagValue, Long wpTagId) {
            built = new LocalTag();
            built.tagId = tagId;
            built.tagValue = tagValue;
            built.wpTagId = wpTagId;
        }

        public LocalTag build() {
            return built;
        }
    }

}
