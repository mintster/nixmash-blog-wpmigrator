package com.nixmash.wp.migrator.service;

import com.nixmash.wp.migrator.db.local.model.*;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.db.wp.model.*;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import com.nixmash.wp.migrator.utils.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by daveburke on 1/20/17.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    private static final String NA = "NA";


    final private WpDbService wpDbService;
    final private LocalDbService localDbService;

    public ImportServiceImpl(WpDbService wpDbService, LocalDbService localDbService) {
        this.wpDbService = wpDbService;
        this.localDbService = localDbService;
    }

    @Override
    public void importPosts() {
        List<WpPost> wpPosts = wpDbService.getPublishedWpPosts();
        List<LocalPost> localPosts = new ArrayList<>();
        for (WpPost wpPost : wpPosts) {
            localPosts.add(ImportUtils.wpToLocalPost(wpPost));
        }
        localDbService.addLocalPosts(localPosts);
    }

    @Override
    public void importCategories() {
        List<WpCategory> wpCategories = wpDbService.getWpCategories();
        List<LocalCategory> localCategories = new ArrayList<>();
        for (WpCategory wpCategory : wpCategories) {
            localCategories.add(ImportUtils.wpToLocalCategory(wpCategory));
        }
        localDbService.addLocalCategories(localCategories);
    }

    @Override
    public void importTags() {
        List<WpTag> wpTags = wpDbService.getWpTags();
        for (WpTag wpTag : wpTags) {
            localDbService.addLocalTag(ImportUtils.wpToLocalTag(wpTag));
        }
    }

    @Override
    public void importPostTags() {
        List<WpPostTag> wpPostTags = wpDbService.getWpPostTags();
        List<LocalPost> posts = localDbService.getLocalPosts();
        List<LocalTag> tags = localDbService.getLocalTags();

        Long tagId = -1L;
        Long postId = -1L;

        List<LocalPostTag> localPostTags = new ArrayList<>();

        for (WpPostTag wpPostTag : wpPostTags) {

            Optional<LocalTag> localTag = tags
                    .stream()
                    .filter(t -> t.getWpTagId().equals(wpPostTag.getWpTagId()))
                    .findFirst();
            if (localTag.isPresent()) {
                tagId = localTag.get().getTagId();
            }

            Optional<LocalPost> localPost = posts
                    .stream()
                    .filter(p -> p.getWpPostId().equals(wpPostTag.getWpPostId()))
                    .findFirst();
            if (localPost.isPresent()) {
                postId = localPost.get().getPostId();
            }

            if (localTag.isPresent() && localPost.isPresent())
                localPostTags.add(LocalPostTag
                        .getWpBuilder(postId, tagId)
                        .build());

        }

        localDbService.addLocalPostTags(localPostTags);
    }


    @Override
    public void importPostCategories() {

        List<WpPostCategory> wpcs = wpDbService.getWpPostCategories();

        List<LocalPost> posts = localDbService.getLocalPosts();
        List<LocalCategory> categories = localDbService.getLocalCategories();

        Long categoryId = -1L;
        Long postId = -1L;

        List<LocalPostCategory> localPostCategories = new ArrayList<>();

        for (WpPostCategory wpc : wpcs) {

            Optional<LocalCategory> localCategory = categories
                    .stream()
                    .filter(c -> c.getWpCategoryId().equals(wpc.getWpCategoryId()))
                    .findFirst();
            if (localCategory.isPresent()) {
                categoryId = localCategory.get().getCategoryId();
            }

            Optional<LocalPost> localPost = posts
                    .stream()
                    .filter(p -> p.getWpPostId().equals(wpc.getWpPostId()))
                    .findFirst();
            if (localPost.isPresent()) {
                postId = localPost.get().getPostId();
            }

            if (localCategory.isPresent() && localPost.isPresent())
                localPostCategories.add(LocalPostCategory
                        .getWpBuilder(postId, categoryId)
                        .build());

        }

        localDbService.addLocalPostCategories(localPostCategories);
    }

}
