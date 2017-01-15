package com.nixmash.wp.migrator.components;

import com.nixmash.wp.migrator.model.LocalPost;
import com.nixmash.wp.migrator.service.WpImportService;
import com.nixmash.wp.migrator.service.WpLocalService;
import org.kamranzafar.spring.wpapi.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    }

    private void showWpPosts() {

        Post[] posts = wpImportService.getPosts(10);
        for (Post post : posts) {
            System.out.println(post.getTitle());
        }
    }

    private void showLocalPosts() {
        List<LocalPost> posts = wpLocalService.getLocalPosts();
        for (LocalPost post : posts) {
            System.out.println(post.getPostTitle());
        }
    }

}
