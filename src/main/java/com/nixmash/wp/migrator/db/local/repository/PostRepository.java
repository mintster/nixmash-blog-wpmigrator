package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.enums.PostType;
import com.nixmash.wp.migrator.db.local.model.LocalPost;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by daveburke on 5/31/16.
 */
public interface PostRepository extends PagingAndSortingRepository<LocalPost, Long> {

    LocalPost findByPostId(Long postId) throws DataAccessException;

    @Query("select distinct p from LocalPost p left join fetch p.tags t")
    List<LocalPost> findAllWithDetail();

    LocalPost findByPostNameIgnoreCase(String postName) throws DataAccessException;

    @Query("select distinct p from LocalPost p left join p.tags t where p.isPublished = true and t.tagId = ?1")
    Page<LocalPost> findByTagId(long tagId, Pageable pageable);

    @Query("select distinct p from LocalPost p left join p.tags t where p.isPublished = true and t.tagId = ?1")
    List<LocalPost> findAllByTagId(long tagId);

    List<LocalPost> findAll(Sort sort);
    List<LocalPost> findAll();

    List<LocalPost> findByIsPublishedTrue(Sort sort);

    Page<LocalPost> findByIsPublishedTrue(Pageable pageable);

    @Query(value = "SELECT " +
            "GROUP_CONCAT(distinct(upper(substr(post_title,1,1))) SEPARATOR '')" +
            " FROM posts WHERE is_published = true", nativeQuery = true)
    String getAlphaLinkString();

    @Query("select distinct p from LocalPost p where p.displayType = 'SINGLEPHOTO_POST'")
    List<LocalPost> findSinglePhotoPosts(Sort sort);

    @Query("select distinct p from LocalPost p left join p.tags t where p.isPublished = true and p.postType = ?1")
    List<LocalPost> findAllPublishedByPostType(PostType postType);

    @Query("select distinct p from LocalPost p left join p.tags t where p.isPublished = true and p.postType = ?1")
    Page<LocalPost> findPublishedByPostTypePaged(PostType postType, Pageable pageable);

}
