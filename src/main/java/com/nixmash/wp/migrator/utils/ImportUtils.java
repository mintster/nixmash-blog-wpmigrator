package com.nixmash.wp.migrator.utils;

import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.wp.model.WpPost;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by daveburke on 1/20/17.
 */
public class ImportUtils {

    public static LocalPost wpToLocalPost(WpPost wpPost) {
        return LocalPost.getBuilder(
                wpPost.getUserId(),
                wpPost.getPostTitle(),
                wpPost.getPostName(),
                ZonedDateTime.ofInstant(wpPost.getPostDate().toInstant(), ZoneId.of("UTC")),
                ZonedDateTime.ofInstant(wpPost.getPostModified().toInstant(), ZoneId.of("UTC")),
                wpPost.getPostId())
                .build();
    }
}
