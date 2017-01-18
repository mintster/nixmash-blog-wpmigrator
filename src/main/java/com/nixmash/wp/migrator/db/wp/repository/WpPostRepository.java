package com.nixmash.wp.migrator.db.wp.repository;

import com.nixmash.wp.migrator.db.wp.model.WpPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by daveburke on 1/18/17.
 */
public interface WpPostRepository extends CrudRepository<WpPost, Long>{

    List<WpPost> findAll();

    @Query("select wp from WpPost wp where wp.postType = 'post' and wp.postStatus = 'publish'")
    List<WpPost> findPublishedPosts();
}
