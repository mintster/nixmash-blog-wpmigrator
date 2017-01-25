package com.nixmash.wp.migrator.components;

import com.nixmash.wp.migrator.db.local.dto.LocalUserDTO;
import com.nixmash.wp.migrator.db.local.service.LocalDbService;
import com.nixmash.wp.migrator.db.wp.service.WpDbService;
import com.nixmash.wp.migrator.service.ImportService;
import com.nixmash.wp.migrator.service.WpApiService;
import com.nixmash.wp.migrator.utils.ImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.nixmash.wp.migrator.utils.ImportUtils.timeMark;
import static com.nixmash.wp.migrator.utils.ImportUtils.totalTime;

/**
 * Created by daveburke on 1/13/17.
 */
@Component
public class WpUI {

    private final WpApiService wpApiService;
    private final LocalDbService localDbService;
    private final WpDbService wpDbService;
    private final ImportService importService;
    private final BloggerSettings bloggerSettings;

    @Autowired
    public WpUI(WpApiService wpApiService, LocalDbService localDbService, WpDbService wpDbService, ImportService importService, BloggerSettings bloggerSettings) {
        this.wpApiService = wpApiService;
        this.localDbService = localDbService;
        this.wpDbService = wpDbService;
        this.importService = importService;
        this.bloggerSettings = bloggerSettings;
    }

    public void init() {
        doImport();
    }

    private void doImport() {
        long start;
        long end;

        long importStart = timeMark();
        long importComplete = timeMark();


        start = timeMark();
        importService.importPosts();
        end = timeMark();
        System.out.println("Total time importPosts(): " + totalTime(start, end));
        int postCount = localDbService.getLocalPosts().size();

        start = timeMark();
        importService.importTags();
        end = timeMark();
        System.out.println("Total time importTags(): " + totalTime(start, end));
        int tagCount = localDbService.getLocalTags().size();

        start = timeMark();
        importService.importPostTags();
        end = timeMark();
        System.out.println("Total time importPostTags(): " + totalTime(start, end));

        start = timeMark();
        importService.importCategories();
        end = timeMark();
        System.out.println("Total time importCategories(): " + totalTime(start, end));
        int categoryCount = localDbService.getLocalCategories().size();

        start = timeMark();
        importService.importPostCategories();
        end = timeMark();
        System.out.println("Total time importPostCategories(): " + totalTime(start, end));

        start = timeMark();
        importService.updatePostContent();
        end = timeMark();
        System.out.println("Total time updatePostContent(): " + totalTime(start, end));

        localDbService.updateLocalUser(getBlogger());

        importComplete = timeMark();

        System.out.println();
        print("Migration complete!");
        print("Number of Posts Imported: " + postCount);
        print("Number of Tags Imported: " + tagCount);
        print("Number of Categories Imported: " + categoryCount);
        print("Total Execution Time: " + totalTime(importStart, importComplete));
        print("See README for any additional setup notes.");
        System.out.println();
    }

    private LocalUserDTO getBlogger() {
        return ImportUtils.updateLocalUserDTO(
                bloggerSettings.getUsername(),
                bloggerSettings.getEmail(),
                bloggerSettings.getFirstName(),
                bloggerSettings.getLastName()
        );
    }

    private void print(String section) {
        System.out.println(String.format("%s --------------------------------- */", section.toUpperCase()));
    }
}
