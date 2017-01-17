package com.nixmash.wp.migrator.service;


import com.nixmash.wp.migrator.model.LocalCategory;
import com.nixmash.wp.migrator.model.LocalPost;
import com.nixmash.wp.migrator.model.LocalTag;
import com.nixmash.wp.migrator.model.LocalUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by daveburke on 1/13/17.
 */
public interface WpLocalService {

    List<LocalPost> getLocalPosts();

    @Transactional(readOnly = true)
    Collection<LocalUser> getLocalUsers();

    @Transactional(readOnly = true)
    Set<LocalTag> getLocalTags();

    @Transactional(readOnly = true)
    Set<LocalCategory> getLocalCategories();
}
