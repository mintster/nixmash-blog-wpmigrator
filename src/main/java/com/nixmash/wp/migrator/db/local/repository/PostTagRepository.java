package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.LocalPostTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostTagRepository extends CrudRepository<LocalPostTag, Long> {

    List<LocalPostTag> findAll();
}
