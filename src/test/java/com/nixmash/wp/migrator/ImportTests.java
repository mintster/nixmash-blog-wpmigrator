package com.nixmash.wp.migrator;

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

import static com.nixmash.wp.migrator.config.JpaTestConfig.LOCAL;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
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
    public void clearH2Data() {
        String sql = "DELETE FROM posts;" +
                "ALTER TABLE posts ALTER COLUMN post_id RESTART WITH 1;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Test
    public void importPostsTest() throws Exception {
       importService.importPosts();
        List<LocalPost> posts = localDbService.getLocalPosts();
        assertNotNull(posts);
        assertThat(posts.size(), greaterThan(0));
    }

}
