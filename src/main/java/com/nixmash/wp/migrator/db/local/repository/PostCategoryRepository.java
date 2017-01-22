package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalPostCategory;
import org.springframework.data.repository.CrudRepository;

public interface PostCategoryRepository extends CrudRepository<LocalPostCategory, Long> {

}
