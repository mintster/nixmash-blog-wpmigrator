package com.nixmash.wp.migrator.components;

import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.local.model.LocalTag;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import com.nixmash.wp.migrator.service.WpImportService;
import org.kamranzafar.spring.wpapi.Category;
import org.kamranzafar.spring.wpapi.Post;
import org.kamranzafar.spring.wpapi.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by daveburke on 1/13/17.
 */
@Component
public class WpUI {

    private final WpImportService wpImportService;
    private final LocalDbService localDbService;
    private final WpDbService wpDbService;

    @Autowired
    public WpUI(WpImportService wpImportService, LocalDbService localDbService, WpDbService wpDbService) {
        this.wpImportService = wpImportService;
        this.localDbService = localDbService;
        this.wpDbService = wpDbService;
    }

    public void init() {
        showWpDbPosts();
        showLocalDbPosts();
//        showLocalDbTags();
//        showWpApiPosts();
//        showWpApiTags();
//        showWpApiCategories();
    }


    private void showWpDbPosts() {
        List<WpPost> posts = wpDbService.getWpPosts();
        for (int i = 0; i < 10; i++) {
            System.out.println(posts.get(i).getPostTitle());
        }
    }

    private void showWpApiPosts() {
        printHeading("post titles");
        Post[] posts = wpImportService.getPosts(10);
        for (Post post : posts) {
            System.out.println(MessageFormat.format("{0,number,#} : {1}", post.getId(), post.getTitle()));
        }
    }

    private void showWpApiTags() {
        printHeading("tags");
        Tag[] tags = wpImportService.getTags(10);
        for (Tag tag : tags) {
            System.out.println(MessageFormat.format("{0} : {1} : {2}", tag.getId(), tag.getName(), tag.getTaxonomy()));
        }
    }

    private void showWpApiCategories() {
        printHeading("categories");
        Category[] categories = wpImportService.getCategories(10);
        for (Category category : categories) {
            System.out.println(MessageFormat.format("{0} : {1} : {2}", category.getId(), category.getName(), category.getTaxonomy()));
        }
    }

    private void showLocalDbPosts() {
        List<LocalPost> posts = localDbService.getLocalPosts();
        for (LocalPost post : posts) {
            System.out.println(post.getPostTitle());
        }
    }

    private void showLocalDbTags() {
        Set<LocalTag> tags = localDbService.getLocalTags();
        for (LocalTag tag : tags) {
            System.out.println(tag.getTagValue());
        }
    }

    private void printHeading(String section) {
        System.out.println();
        System.out.println(String.format("%s --------------------------------- */", section.toUpperCase()));
        System.out.println();
    }
}
