package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.components.BloggerSettings;
import com.nixmash.wp.migrator.db.local.dto.LocalUserDTO;
import com.nixmash.wp.migrator.db.local.model.*;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.enums.PostType;
import com.nixmash.wp.migrator.utils.ImportUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static com.nixmash.wp.migrator.config.JpaTestConfig.LOCAL;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * Created by daveburke on 1/15/17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@Transactional
public class LocalDbTests extends WpSpringContext {

    private static final String NA = "NA";

    @Autowired
    private LocalDbService localDbService;

    @Autowired
    private BloggerSettings bloggerSettings;

    @PersistenceContext(unitName = LOCAL)
    EntityManager entityManager;

    @Before
    public void clearPosts() {
        String sql = "DELETE FROM posts;" +
                "ALTER TABLE posts ALTER COLUMN post_id RESTART WITH 1;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    // region retrieve Posts, Users , Categories and Tags

    @Test
    public void getLocalPostsTest() throws Exception {
       List<LocalPost> posts = localDbService.getLocalPosts();
        assertNotNull(posts);
        assertEquals(posts.size(), 0);
    }

    @Test
    public void getLocalTagsTest() throws Exception {
        List<LocalTag> tags = localDbService.getLocalTags();
        assertNotNull(tags);
        assertEquals(tags.size(), 0);
    }

    @Test
    public void getLocalCategoriesTest() throws Exception {
        List<LocalCategory> categories= localDbService.getLocalCategories();
        assertNotNull(categories);
        assertEquals(categories.size(), 0);
    }

    @Test
    public void getLocalUsersTest() throws Exception {
        Collection<LocalUser> users = localDbService.getLocalUsers();
        assertNotNull(users);
        assertThat(users.size(), greaterThan(0));
    }

    @Test
    public void getLocalPostCategoriesTest() throws Exception {
        List<LocalPostCategory> localPostCategories = localDbService.getLocalPostCategories();
        assertNotNull(localPostCategories);
        assertEquals(localPostCategories.size(), 0);
    }

    // endregion

    // region saving data tests

    @Test
    public void addPost() {
        LocalPost post = LocalPost.getBuilder(1L, "New Title", "new-title", ZonedDateTime.now(), ZonedDateTime.now(), 100L).build();
        LocalPost saved = localDbService.addLocalPost(post);
        assertNotNull(saved);
        assertEquals(saved.getPostType(), PostType.POST);

        LocalPost found = localDbService.getLocalPostByPostId(saved.getPostId());
        assertNotNull(found);
    }

    @Test
    public void updatePasswordTest() {
        String OLD_PASSWORD = "password";
        String NEW_PASSWORD = "newword";
        String encodedPassword;

        LocalUser localUser = localDbService.getLocalUserById(1L);
        encodedPassword = localUser.getPassword();
        assertTrue(new BCryptPasswordEncoder().matches(OLD_PASSWORD, encodedPassword));

        localUser.setPassword(NEW_PASSWORD);
        localDbService.updateLocalUserPassword(localUser);
        LocalUser updatedUser = localDbService.getLocalUserById(1L);
        encodedPassword = updatedUser.getPassword();
        assertTrue(new BCryptPasswordEncoder().matches(NEW_PASSWORD, encodedPassword));

    }

    @Test
    public void updateLocalUserTest() {

        String NEW_USERNAME = "bobby";

        LocalUser localUser = localDbService.getLocalUserById(1L);
        String OLD_USERNAME = localUser.getUsername();
        assertEquals(localUser.getUsername(), OLD_USERNAME);

        LocalUserDTO localUserDTO = ImportUtils.updateLocalUserDTO(
                NEW_USERNAME, "bobby@aol.com", "Bob", "Shmob");
        LocalUser updatedUser = localDbService.updateLocalUser(localUserDTO);
        assertEquals(updatedUser.getUsername(), NEW_USERNAME);

        LocalUser foundUser = localDbService.getLocalUserById(1L);
        assertEquals(foundUser.getUsername(), NEW_USERNAME);

    }

    // endregion

    // region Update Blogger Properties Tests

    @Test
    public void updateBlogger() throws Exception {
        String BAMBAM_EMAIL = "bambam@aol.com";

        assertEquals(bloggerSettings.getEmail(), BAMBAM_EMAIL);

        LocalUser localUser = localDbService.getLocalUserById(1L);
        String localUserKey = localUser.getUserKey();
        assertNotEquals(localUser.getUsername(), bloggerSettings.getUsername());

        LocalUserDTO localUserDTO = ImportUtils.updateLocalUserDTO(
                bloggerSettings.getUsername(),
                bloggerSettings.getEmail(),
                bloggerSettings.getFirstName(),
                bloggerSettings.getLastName()
        );
        LocalUser saved = localDbService.updateLocalUser(localUserDTO);
        assertEquals(saved.getEmail(), BAMBAM_EMAIL);
        assertNotEquals(localUserKey, saved.getUserKey());

    }

    // endregion
}
