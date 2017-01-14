package com.nixmash.wp.migrator.service;

import org.kamranzafar.spring.wpapi.Post;
import org.kamranzafar.spring.wpapi.client.WpApiClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daveburke on 1/13/17.
 */
@Service("wpImportService")
@Transactional
public class WpImportServiceImpl implements WpImportService {

    private final WpApiClient wpApiClient;

    public WpImportServiceImpl(WpApiClient wpApiClient) {
        this.wpApiClient = wpApiClient;
    }

    @Override
    public Post[] getPosts(int count) {
        Map<String, String> params = new HashMap<>();
        params.put("per_page", Integer.toString(count));
        return wpApiClient.getPostService().getAll(params);
    }

}
