package com.nixmash.wp.migrator.db.wp.service;

import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.repository.WpPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by daveburke on 1/18/17.
 */
@Service(value = "wpDbService")
@Transactional
public class WpDbServiceImpl implements WpDbService{

    private final WpPostRepository wpPostRepository;

    public WpDbServiceImpl(WpPostRepository wpPostRepository) {
        this.wpPostRepository = wpPostRepository;
    }

    @Override
    public List<WpPost> getWpPosts() {
        return wpPostRepository.findPublishedPosts();
    }
}
