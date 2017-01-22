/* ------------------------------------------------------------------------------------- */
#
#   Views added to WordPress database to facility data import
#
/* ------------------------------------------------------------------------------------- */


SET FOREIGN_KEY_CHECKS=0;

DROP VIEW IF EXISTS v_nixmash_wppost_categories;
CREATE VIEW v_nixmash_wppost_categories AS
  SELECT
    t.term_id AS wp_category_id,
    t.name    AS category_value,
    p.id AS wp_post_id,
    p.post_title,
    0 as category_id
  FROM (nixmashwp.wp_terms t
    JOIN nixmashwp.wp_term_taxonomy tx ON t.term_id = tx.term_id
    JOIN nixmashwp.wp_term_relationships r ON r.term_taxonomy_id = tx.term_taxonomy_id
    JOIN nixmashwp.wp_posts p ON p.id = r.object_id)
  WHERE (tx.taxonomy = 'category' AND p.post_status = 'publish' AND p.post_type = 'post')


DROP VIEW IF EXISTS v_nixmash_wppost_tags;
CREATE VIEW v_nixmash_wppost_tags AS
  SELECT
    t.term_id AS wp_tag_id,
    t.name    AS tag_value,
    p.id AS wp_post_id,
    p.post_title,
    0 as tag_id
  FROM (nixmashwp.wp_terms t
    JOIN nixmashwp.wp_term_taxonomy tx ON t.term_id = tx.term_id
    JOIN nixmashwp.wp_term_relationships r ON r.term_taxonomy_id = tx.term_taxonomy_id
    JOIN nixmashwp.wp_posts p ON p.id = r.object_id)
  WHERE (tx.taxonomy = 'post_tag' AND p.post_status = 'publish' AND p.post_type = 'post')


DROP VIEW IF EXISTS v_nixmash_wptags;
CREATE VIEW v_nixmash_wptags AS
  SELECT
    t.term_id AS wp_tag_id,
    t.name    AS tag_value
  FROM (nixmashwp.wp_terms t
    JOIN nixmashwp.wp_term_taxonomy tx ON ((t.term_id = tx.term_id)))
  WHERE (tx.taxonomy = 'post_tag');


DROP VIEW IF EXISTS v_nixmash_wpcategories;
CREATE VIEW v_nixmash_wpcategories AS
  SELECT
    t.term_id AS wp_category_id,
    t.name    AS category_value
  FROM (nixmashwp.wp_terms t
    JOIN nixmashwp.wp_term_taxonomy tx ON ((t.term_id = tx.term_id)))
  WHERE (tx.taxonomy = 'category');

