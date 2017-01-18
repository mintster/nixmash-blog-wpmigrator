package com.nixmash.wp.migrator.db.local.service;

import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.local.model.LocalTag;
import com.nixmash.wp.migrator.db.local.model.LocalUser;
import com.nixmash.wp.migrator.db.local.repository.CategoryRepository;
import com.nixmash.wp.migrator.db.local.repository.PostRepository;
import com.nixmash.wp.migrator.db.local.repository.TagRepository;
import com.nixmash.wp.migrator.db.local.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by daveburke on 1/13/17.
 */
@Service("wpLocalService")
@Transactional
public class LocalDbServiceImpl implements LocalDbService {

    private static final Logger logger = LoggerFactory.getLogger(LocalDbServiceImpl.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public LocalDbServiceImpl(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    // region Posts

    @Override
    @Transactional(readOnly = true)
    public List<LocalPost> getLocalPosts() {
        return postRepository.findAll(sortByPostDateDesc());
    }

    // endregion

    // region Users

    @Override
    @Transactional(readOnly = true)
    public Collection<LocalUser> getLocalUsers() {
        return  userRepository.findAll();
    }

    // endregion

    // region Tags

    @Override
    @Transactional(readOnly = true)
    public Set<LocalTag> getLocalTags() {
        return tagRepository.findAll();
    }

    // endregion

    // region Categories

    @Override
    @Transactional(readOnly = true)
    public  Set<LocalCategory> getLocalCategories() {
        return categoryRepository.findAll();
    }

    // endregion

    public Sort sortByPostDateDesc() {
        return new Sort(Sort.Direction.DESC, "postDate");
    }

}
