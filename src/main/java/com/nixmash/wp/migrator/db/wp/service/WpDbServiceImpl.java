package com.nixmash.wp.migrator.db.wp.service;

import com.nixmash.wp.migrator.db.wp.model.WpCategory;
import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.repository.WpCategoryRepository;
import com.nixmash.wp.migrator.db.wp.repository.WpPostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.nixmash.wp.migrator.config.JpaConfig.WP;

/**
 * Created by daveburke on 1/18/17.
 */
@Service(value = "wpDbService")
@Transactional
public class WpDbServiceImpl implements WpDbService {

    @PersistenceContext(unitName = WP)
    private EntityManager em;

    private final WpPostRepository wpPostRepository;
    private final WpCategoryRepository wpCategoryRepository;

    public WpDbServiceImpl(WpPostRepository wpPostRepository, WpCategoryRepository wpCategoryRepository) {
        this.wpPostRepository = wpPostRepository;
        this.wpCategoryRepository = wpCategoryRepository;
    }

    @Override
    public List<WpPost> getPublishedWpPosts() {
        return wpPostRepository.findPublishedPosts(sortById());
    }

    @Override
    public List<WpCategory> getCategories() {
        return wpCategoryRepository.findAll();
    }

    @Override
    public String getSampleTitle() {
        String sql = "SELECT post_title FROM wp_posts WHERE id = 3204;";
        return String.valueOf(em.createNativeQuery(sql).getSingleResult());
    }

    private Sort sortById() {
        return new Sort(Sort.Direction.ASC, "postId");
    }
}
