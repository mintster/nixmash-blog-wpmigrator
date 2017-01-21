package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import com.nixmash.wp.migrator.db.local.model.LocalPost;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.service.ImportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static com.nixmash.wp.migrator.config.JpaTestConfig.LOCAL;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by daveburke on 1/15/17.
 */
@RunWith(SpringRunner.class)
@Transactional
public class ImportTests extends WpSpringContext {

    private static final String NA = "NA";

    @Autowired
    private ImportService importService;

    @Autowired
    private LocalDbService localDbService;

    @PersistenceContext(unitName = LOCAL)
    EntityManager entityManager;

    @Before
    public void clearPostData() {
        String sql = "SET FOREIGN_KEY_CHECKS = 0;" +
                                    "TRUNCATE TABLE posts; " +
                                    "TRUNCATE TABLE  categories; " +
                                    "TRUNCATE TABLE  tags;" +
                                    "TRUNCATE TABLE  post_tag_ids;" +
                                    "TRUNCATE TABLE  post_category_ids;" +
                                    "SET FOREIGN_KEY_CHECKS = 1;";
        entityManager.createNativeQuery(sql).executeUpdate();
        List<LocalPost> posts = localDbService.getLocalPosts();
        assertEquals(posts.size(), 0);
    }

    // region import tests

    @Test
    public void importPostsTest() {
        importService.importPosts();
        List<LocalPost> posts = localDbService.getLocalPosts();
        assertThat(posts.size(), greaterThan(0));
    }

    @Test
    public void importCategoriesTest() {
        importService.importCategories();
        Set<LocalCategory> categories = localDbService.getLocalCategories();
        assertThat(categories.size(), greaterThan(0));
    }

    // endregion
}
