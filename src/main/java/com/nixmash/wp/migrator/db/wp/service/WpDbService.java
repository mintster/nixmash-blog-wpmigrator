package com.nixmash.wp.migrator.db.wp.service;

import com.nixmash.wp.migrator.db.wp.model.WpPost;

import java.util.List;

/**
 * Created by daveburke on 1/18/17.
 */
public interface WpDbService {

    List<WpPost> getPublishedWpPosts();

    String getSampleTitle();
}
