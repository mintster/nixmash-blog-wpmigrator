package com.nixmash.wp.migrator.components;

import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.service.WpImportService;
import com.nixmash.wp.migrator.db.local.service.WpLocalService;
import org.kamranzafar.spring.wpapi.Category;
import org.kamranzafar.spring.wpapi.Post;
import org.kamranzafar.spring.wpapi.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by daveburke on 1/13/17.
 */
@Component
public class WpUI {

    private final WpImportService wpImportService;
    private final WpLocalService wpLocalService;

    @Autowired
    public WpUI(WpImportService wpImportService, WpLocalService wpLocalService) {
        this.wpImportService = wpImportService;
        this.wpLocalService = wpLocalService;
    }

    public void init() {
        showLocalPosts();
//        showWpPosts();
//        showWpTags();
//        showWpCategories();
    }

    private void showWpPosts() {
        printHeading("post titles");
        Post[] posts = wpImportService.getPosts(10);
        for (Post post : posts) {
            System.out.println(MessageFormat.format("{0,number,#} : {1}", post.getId(), post.getTitle()));
        }
    }

    private void showWpTags() {
        printHeading("tags");
        Tag[] tags = wpImportService.getTags(10);
        for (Tag tag : tags) {
            System.out.println(MessageFormat.format("{0} : {1} : {2}", tag.getId(), tag.getName(), tag.getTaxonomy()));
        }
    }

    private void showWpCategories() {
        printHeading("categories");
        Category[] categories = wpImportService.getCategories(10);
        for (Category category : categories) {
            System.out.println(MessageFormat.format("{0} : {1} : {2}", category.getId(), category.getName(), category.getTaxonomy()));
        }
    }

    private void showLocalPosts() {
        List<LocalPost> posts = wpLocalService.getLocalPosts();
        for (LocalPost post : posts) {
            System.out.println(post.getPostTitle());
        }
    }

    private void printHeading(String section) {
        System.out.println();
        System.out.println(String.format("%s --------------------------------- */", section.toUpperCase()));
        System.out.println();
    }
}
