package com.nixmash.wp.migrator.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by daveburke on 1/20/17.
 */
public interface ImportService {
    void importPosts();
    void importCategories();

    void importTags();

    @Transactional
    void updatePostContent();

    void importPostTags();

    void importPostCategories();
}
