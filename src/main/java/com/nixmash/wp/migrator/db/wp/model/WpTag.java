package com.nixmash.wp.migrator.db.wp.model;

import org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime;

import javax.persistence.*;

/**
 * Created by daveburke on 1/21/17.
 */
@Entity
@Table(name = "v_nixmash_wptags")
@SqlResultSetMapping(name = "AllWpTags",
        classes = {
                @ConstructorResult(
                        targetClass = WpTag.class,
                        columns = {
                                @ColumnResult(name = "wp_tag_id", type = Long.class),
                                @ColumnResult(name = "tag_value")
                        }
                )
        }
)
public class WpTag {
    private Long wpTagId;
    private String tagValue;

    @Id
    @Column(name = "wp_tag_id")
    public Long getWpTagId() {
        return wpTagId;
    }

    public void setWpTagId(Long wpTagId) {
        this.wpTagId = wpTagId;
    }

    @Basic
    @Column(name = "tag_value")
    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public WpTag() {
    }

    public WpTag(Long wpTagId, String tagValue) {
        this.wpTagId = wpTagId;
        this.tagValue = tagValue;
    }

    @Override
    public String toString() {
        return "WpTag{" +
                "wpTagId=" + wpTagId +
                ", tagValue='" + tagValue + '\'' +
                '}';
    }
}
