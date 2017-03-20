/* ------------------------------------------------------------------------------

   Views added to WordPress database to facility data import
    WordPress NixMash Spring Migrator Version v1.0.0

--------------------------------------------------------------------------- */


SET FOREIGN_KEY_CHECKS=0;

DROP VIEW IF EXISTS v_nixmash_wppostcategories;
CREATE VIEW v_nixmash_wppostcategories AS
  SELECT
    UUID() as id,
    t.term_id AS wp_category_id,
    p.id AS wp_post_id
  FROM (wp_terms t
    JOIN wp_term_taxonomy tx ON t.term_id = tx.term_id
    JOIN wp_term_relationships r ON r.term_taxonomy_id = tx.term_taxonomy_id
    JOIN wp_posts p ON p.id = r.object_id)
  WHERE (tx.taxonomy = 'category' AND p.post_status = 'publish' AND p.post_type = 'post');

DROP VIEW IF EXISTS v_nixmash_wpposttags;
CREATE VIEW v_nixmash_wpposttags AS
  SELECT
    UUID() AS id,
    t.term_id AS wp_tag_id,
    p.id AS wp_post_id
  FROM (wp_terms t
    JOIN wp_term_taxonomy tx ON t.term_id = tx.term_id
    JOIN wp_term_relationships r ON r.term_taxonomy_id = tx.term_taxonomy_id
    JOIN wp_posts p ON p.id = r.object_id)
  WHERE (tx.taxonomy = 'post_tag' AND p.post_status = 'publish' AND p.post_type = 'post');


DROP VIEW IF EXISTS v_nixmash_wptags;
CREATE VIEW v_nixmash_wptags AS
  SELECT
    t.term_id AS wp_tag_id,
    t.name    AS tag_value
  FROM (wp_terms t
    JOIN wp_term_taxonomy tx ON ((t.term_id = tx.term_id)))
  WHERE (tx.taxonomy = 'post_tag');


DROP VIEW IF EXISTS v_nixmash_wpcategories;
CREATE VIEW v_nixmash_wpcategories AS
  SELECT
    t.term_id AS wp_category_id,
    t.name    AS category_value
  FROM (wp_terms t
    JOIN wp_term_taxonomy tx ON ((t.term_id = tx.term_id)))
  WHERE (tx.taxonomy = 'category');

