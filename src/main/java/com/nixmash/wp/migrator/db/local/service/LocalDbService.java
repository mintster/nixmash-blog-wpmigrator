package com.nixmash.wp.migrator.db.local.service;


import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.local.model.LocalTag;
import com.nixmash.wp.migrator.db.local.model.LocalUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by daveburke on 1/13/17.
 */
public interface LocalDbService {

    List<LocalPost> getLocalPosts();

    @Transactional
    LocalPost addLocalPost(LocalPost post);

    @Transactional
    LocalPost getLocalPostByPostId(Long postId);

    @Transactional(readOnly = true)
    Collection<LocalUser> getLocalUsers();

    @Transactional(readOnly = true)
    Set<LocalTag> getLocalTags();

    @Transactional(readOnly = true)
    Set<LocalCategory> getLocalCategories();
}
