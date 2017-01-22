package com.nixmash.wp.migrator.db.wp.service;

import com.nixmash.wp.migrator.db.wp.model.WpCategory;
import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.model.WpPostCategory;
import com.nixmash.wp.migrator.db.wp.model.WpTag;

import java.util.List;

/**
 * Created by daveburke on 1/18/17.
 */
public interface WpDbService {

    List<WpPost> getPublishedWpPosts();

    List<WpCategory> getWpCategories();

    @SuppressWarnings("unchecked")
    List<WpPostCategory> getWpPostCategories();

    @SuppressWarnings("unchecked")
    List<WpTag> getWpTags();

    String getSampleTitle();
}
