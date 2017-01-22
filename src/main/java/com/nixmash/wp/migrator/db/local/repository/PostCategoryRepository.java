package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalPostCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostCategoryRepository extends CrudRepository<LocalPostCategory, Long> {

    List<LocalPostCategory> findAll();
}
