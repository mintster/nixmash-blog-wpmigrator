package com.nixmash.wp.migrator.db.local.service;

import com.nixmash.wp.migrator.db.local.model.*;
import com.nixmash.wp.migrator.db.local.repository.*;
import com.nixmash.wp.migrator.db.wp.model.WpPostCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

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
    private final PostCategoryRepository postCategoryRepository;
    private final PostTagRepository postTagRepository;

    @Autowired
    public LocalDbServiceImpl(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository, CategoryRepository categoryRepository, PostCategoryRepository postCategoryRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.postTagRepository = postTagRepository;
    }

    // region Posts

    @Override
    @Transactional(readOnly = true)
    public List<LocalPost> getLocalPosts() {
        return postRepository.findAll(sortByPostDateDesc());
    }

    @Override
    @Transactional
    public LocalPost addLocalPost(LocalPost post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void addLocalPosts(List<LocalPost> posts) {
        postRepository.save(posts);
    }

    @Override
    @Transactional
    public LocalPost getLocalPostByPostId(Long postId) {
        return postRepository.findByPostId(postId);
    }

    @Override
    @Transactional
    public LocalPost getLocalPostByWpPostId(Long wpPostId) {
        return postRepository.findByWpPostId(wpPostId);
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
    public List<LocalTag> getLocalTags() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public LocalTag addLocalTag(LocalTag localTag) {
        return tagRepository.save(localTag);
    }

    // endregion

    // region Categories

    @Override
    @Transactional(readOnly = true)
    public  List<LocalCategory> getLocalCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public LocalCategory addLocalCategory(LocalCategory localCategory) {
        return categoryRepository.save(localCategory);
    }

    @Override
    @Transactional
    public void addLocalCategories(List<LocalCategory> localCategories) {
        categoryRepository.save(localCategories);
    }

    // endregion

    // region Post Categories

    @Override
    public Long getLocalPostCategoryId(WpPostCategory wpPostCategory) {
        return categoryRepository.findByWpCategoryId(wpPostCategory.getWpCategoryId()).getCategoryId();
    }

    @Override
    public LocalPostCategory addLocalPostCategory(LocalPostCategory localPostCategory) {
        return postCategoryRepository.save(localPostCategory);
    }

    @Override
    @Transactional
    public void addLocalPostCategories(List<LocalPostCategory> localPostCategories) {
        postCategoryRepository.save(localPostCategories);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<LocalPostCategory> getLocalPostCategories() {
        return postCategoryRepository.findAll();
    }

    // endregion

    // region Post Tags

    @Override
    @Transactional(readOnly = true)
    public  List<LocalPostTag> getLocalPostTags() {
        return postTagRepository.findAll();
    }

    @Override
    @Transactional
    public void addLocalPostTags(List<LocalPostTag> localPostTags) {
        postTagRepository.save(localPostTags);
    }

    // endregion


    public Sort sortByPostDateDesc() {
        return new Sort(Sort.Direction.DESC, "postDate");
    }

}
