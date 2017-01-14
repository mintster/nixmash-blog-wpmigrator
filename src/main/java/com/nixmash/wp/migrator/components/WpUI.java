package com.nixmash.wp.migrator.components;

import com.nixmash.wp.migrator.service.WpImportService;
import org.kamranzafar.spring.wpapi.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by daveburke on 1/13/17.
 */
@Component
public class WpUI {

    private final WpImportService wpImportService;

    @Autowired
    public WpUI(WpImportService wpImportService) {
        this.wpImportService = wpImportService;
    }

    public void init() {
        showPosts();
    }

    private void showPosts() {

        Post[] posts = wpImportService.getPosts(10);
        for (Post post : posts) {
            System.out.println(post.getTitle());
        }
    }

}
