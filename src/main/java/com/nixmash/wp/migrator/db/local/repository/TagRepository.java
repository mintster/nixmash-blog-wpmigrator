package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<LocalTag, Long> {

    LocalTag findByTagValueIgnoreCase(String tagValue);

    List<LocalTag> findAll();

}
