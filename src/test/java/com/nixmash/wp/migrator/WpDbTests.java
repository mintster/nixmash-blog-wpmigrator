package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.nixmash.wp.migrator.config.JpaTestConfig.WP;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by daveburke on 1/15/17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
public class WpDbTests extends WpSpringContext {

    @Autowired
    private WpDbService wpDbService;

    @PersistenceContext(unitName = WP)
    EntityManager emf;

    // region retrieve Posts, Users , Categories and Tags

    @Test
    public void getPublishedWpPostsTest() throws Exception {
        List<WpPost> posts = wpDbService.getPublishedWpPosts();
        assertNotNull(posts);
        assertThat(posts.size(), greaterThan(0));
    }

    @Test
    public void wpEntityManagerFactoryTest() {
        String sql = "SELECT * FROM wp_posts;";
        List<WpPost> wpPosts  = emf.createNativeQuery(sql).getResultList();
        assertNotNull(wpPosts);
        assertThat(wpPosts.size(), greaterThan(0));
    }

    // endregion


}
