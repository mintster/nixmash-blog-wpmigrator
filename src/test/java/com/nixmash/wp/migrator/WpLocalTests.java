package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.local.model.LocalTag;
import com.nixmash.wp.migrator.db.local.model.LocalUser;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by daveburke on 1/15/17.
 */
@RunWith(SpringRunner.class)
public class WpLocalTests extends WpSpringContext {

    @Autowired
    private LocalDbService localDbService;

    // region retrieve Posts, Users , Categories and Tags

    @Test
    public void getLocalPostsTest() throws Exception {
       List<LocalPost> posts = localDbService.getLocalPosts();
        assertNotNull(posts);
        assertThat(posts.size(), greaterThan(0));
    }

    @Test
    public void getLocalTagsTest() throws Exception {
        Set<LocalTag> tags = localDbService.getLocalTags();
        assertNotNull(tags);
        assertThat(tags.size(), greaterThan(0));
    }

    @Test
    public void getLocalCategoriesTest() throws Exception {
        Set<LocalCategory> categories= localDbService.getLocalCategories();
        assertNotNull(categories);
        assertThat(categories.size(), greaterThan(0));
    }

    @Test
    public void getLocalUsersTest() throws Exception {
        Collection<LocalUser> users = localDbService.getLocalUsers();
        assertNotNull(users);
        assertThat(users.size(), greaterThan(0));
    }

    // endregion


}
