package com.nixmash.wp.migrator.service;

import org.kamranzafar.spring.wpapi.Category;
import org.kamranzafar.spring.wpapi.Post;
import org.kamranzafar.spring.wpapi.Tag;
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
        return wpApiClient.getPostService().getAll(getParams(count));
    }

    @Override
    public Tag[] getTags(int count) {
        return wpApiClient.getTagService().getAll(getParams(count));
    }

    @Override
    public Category[] getCategories(int count) {
        return wpApiClient.getCategoryService().getAll(getParams(count));
    }

    private Map<String, String> getParams(int count) {
        Map<String, String> params = new HashMap<>();
        params.put("per_page", Integer.toString(count));
        return params;
    }
}
