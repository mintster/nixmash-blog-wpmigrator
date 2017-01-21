package com.nixmash.wp.migrator.service;

import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.db.wp.model.WpCategory;
import com.nixmash.wp.migrator.db.wp.model.WpPost;
import com.nixmash.wp.migrator.db.wp.model.WpTag;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import com.nixmash.wp.migrator.utils.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daveburke on 1/20/17.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    private static final String NA = "NA";


    final private WpDbService wpDbService;
    final private LocalDbService localDbService;

    public ImportServiceImpl(WpDbService wpDbService, LocalDbService localDbService) {
        this.wpDbService = wpDbService;
        this.localDbService = localDbService;
    }

    @Override
    public void importPosts() {
        List<WpPost> wpPosts = wpDbService.getPublishedWpPosts();
        for (WpPost wpPost : wpPosts) {
            localDbService.addLocalPost(ImportUtils.wpToLocalPost(wpPost));
        }
    }

    @Override
    public void importCategories() {
        List<WpCategory> wpCategories = wpDbService.getWpCategories();
        for (WpCategory wpCategory : wpCategories) {
            localDbService.addLocalCategory(ImportUtils.wpToLocalCategory(wpCategory));
        }
    }

    @Override
    public void importTags() {
        List<WpTag> wpTags= wpDbService.getWpTags();
        for (WpTag wpTag : wpTags) {
            localDbService.addLocalTag(ImportUtils.wpToLocalTag(wpTag));
        }
    }

}
