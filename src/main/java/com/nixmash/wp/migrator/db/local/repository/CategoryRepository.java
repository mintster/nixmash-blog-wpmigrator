package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalCategory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<LocalCategory, Long> {

    LocalCategory findByCategoryValueIgnoreCase(String categoryValue);

    List<LocalCategory> findAll();

    LocalCategory findByWpCategoryId(Long wpCategoryId)  throws DataAccessException;

}
