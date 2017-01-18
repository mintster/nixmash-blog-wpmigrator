package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by daveburke on 1/15/17.
 */
@RunWith(SpringRunner.class)
public class WpDbTests extends WpSpringContext {

    @Autowired
    private WpDbService wpDbService;

    // region retrieve Posts, Users , Categories and Tags

    @Test
    public void getLocalPostsTest() throws Exception {
       List<WpPost> posts = wpDbService.getWpPosts();
        assertNotNull(posts);
        assertThat(posts.size(), greaterThan(0));
    }

    // endregion


}
