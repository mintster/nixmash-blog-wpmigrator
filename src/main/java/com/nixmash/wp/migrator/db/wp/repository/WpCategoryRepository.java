package com.nixmash.wp.migrator.db.wp.repository;

import com.nixmash.wp.migrator.db.wp.model.WpCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by daveburke on 1/18/17.
 */
public interface WpCategoryRepository extends CrudRepository<WpCategory, Long>{

    List<WpCategory> findAll();

}
