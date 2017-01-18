package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.service.WpImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kamranzafar.spring.wpapi.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by daveburke on 1/15/17.
 */
@RunWith(SpringRunner.class)
public class WpApiTests extends WpSpringContext {

    @Autowired
    private WpImportService wpImportService;

    @Test
    public void getimportPostsTests() throws Exception {
        Post[] posts = wpImportService.getPosts(10);
        assertNotNull(posts);
        assertEquals(posts.length, 10);
    }

}
