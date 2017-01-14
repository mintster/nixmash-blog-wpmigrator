package com.nixmash.wp.migrator.service;

import org.kamranzafar.spring.wpapi.Post;

/**
 * Created by daveburke on 1/13/17.
 */
public interface WpImportService {
    Post[] getPosts(int count);
}
