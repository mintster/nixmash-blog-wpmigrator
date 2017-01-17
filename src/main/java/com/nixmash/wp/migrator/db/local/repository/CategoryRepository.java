package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CategoryRepository extends CrudRepository<LocalCategory, Long> {

    LocalCategory findByCategoryValueIgnoreCase(String categoryValue);

    Set<LocalCategory> findAll();

}
