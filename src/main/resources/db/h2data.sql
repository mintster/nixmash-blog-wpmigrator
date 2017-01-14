/*
Navicat MariaDB Data Transfer

Source Server         : mariadb
Source Server Version : 100017
Source Host           : localhost:3306
Source Database       : dev_hibernate

Target Server Type    : MariaDB
Target Server Version : 100017
File Encoding         : 65001

Date: 2015-04-21 17:49:43
*/


/* ------------------------------------------------------------ */
-- Users
/* ------------------------------------------------------------ */

INSERT INTO users (user_id, email, username, password, first_name, last_name, enabled, account_expired, account_locked, credentials_expired, has_avatar, user_key, provider_id)
VALUES
  (1, 'admin@email.com', 'admin', '$2a$10$B9wQFSrr3bfQeUGqxtTDuut1.4YFcA/WFthZaGe1wtb1wgVW./Oiq', 'Admin', 'Jones',
      TRUE, FALSE, FALSE, FALSE, FALSE, '4L4Hr3skHYYMbjkQ', 'SITE');
INSERT INTO users (user_id, email, username, password, first_name, last_name, enabled, account_expired, account_locked, credentials_expired, has_avatar, user_key, provider_id)
VALUES (2, 'keith@aol.com', 'keith', '$2a$10$F2a2W8RtbD99xXd9xtwjbuI4zjSYe04kS.s0FyvQcAIDJfh/6jjLW', 'Keith', 'Obannon',
           TRUE, FALSE, FALSE, FALSE, FALSE, 'HuoPByrU0hC87gz8', 'SITE');


INSERT INTO user_data (user_id, lastlogin_datetime, created_datetime) SELECT
                                                                        user_id,
                                                                       current_timestamp,
                                                                        current_timestamp
                                                                      FROM users;

/* ------------------------------------------------------------ */
-- Authorities
/* ------------------------------------------------------------ */

INSERT INTO authorities (authority_id, authority, is_locked) VALUES (1, 'ROLE_ADMIN', TRUE);
INSERT INTO authorities (authority_id, authority, is_locked) VALUES (2, 'ROLE_USER', TRUE);
INSERT INTO authorities (authority_id, authority, is_locked) VALUES (3, 'ROLE_POSTS', TRUE);

/* ------------------------------------------------------------ */
-- User_Authorities
/* ------------------------------------------------------------ */

INSERT INTO user_authorities (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authorities (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authorities (user_id, authority_id) VALUES (1, 3);
INSERT INTO user_authorities (user_id, authority_id) VALUES (2, 2);


INSERT INTO site_options (option_id, option_name, option_value) VALUES ('1', 'siteName', 'My Site');
INSERT INTO site_options (option_id, option_name, option_value) VALUES ('2', 'siteDescription', 'My Site Description');
INSERT INTO site_options (option_id, option_name, option_value) VALUES ('3', 'addGoogleAnalytics', 'false');
INSERT INTO site_options (option_id, option_name, option_value) VALUES ('4', 'googleAnalyticsTrackingId', 'UA-XXXXXX-7');
INSERT INTO site_options (option_id, option_name, option_value) VALUES ('5', 'integerProperty', '1');
INSERT INTO site_options (option_id, option_name, option_value) VALUES ('6', 'userRegistration', 'ADMINISTRATIVE_APPROVAL');

/* ------------------------------------------------------------ */
-- Posts
/* ------------------------------------------------------------ */

-- INSERT INTO posts (post_id, user_id, post_title, post_name, post_link, post_date, post_modified, post_type, display_type, is_published, post_content, post_source, post_image, click_count, likes_count, value_rating, version) VALUES (1, 1, 'Post One Title', 'post-one-title', 'http://nixmash.com/something', '2016-05-31 13:27:47', '2016-05-31 13:28:01', 'LINK', 'LINK', 1, 'Post One Content', 'nixmash.com', null, 0, 0, 0, 0);
-- INSERT INTO posts (post_id, user_id, post_title, post_name, post_link, post_date, post_modified, post_type, display_type, is_published, post_content, post_source, post_image, click_count, likes_count, value_rating, version) VALUES (2, 1, 'Post Two Title', 'post-two-title', 'http://stackoverflow.com/something', '2016-05-31 14:30:45', '2016-05-31 14:30:47', 'LINK', 'LINK', 1, 'Post Two Content', 'stackoverflow.com', null, 0, 0, 0, 0);
INSERT INTO posts (post_id, user_id, post_title, post_name, post_link, post_date, post_modified, post_type, display_type, is_published, post_content, post_source, post_image, click_count, likes_count, value_rating, version)
VALUES (1, 2, 'Sample Test Post', 'sample-test_post', NULL, '2016-09-30 17:40:18',
            '2016-09-30 17:40:18', 'POST', 'POST', 1,
            '<p><strong>This is a post</strong> for <em>H2 Integration Testing</em></p>', 'NA', NULL, 0, 0, 0, 0);

INSERT INTO tags (tag_id, tag_value) VALUES (1, 'h2sampletag');

INSERT INTO post_tag_ids (post_id, tag_id) VALUES (1, 1);


