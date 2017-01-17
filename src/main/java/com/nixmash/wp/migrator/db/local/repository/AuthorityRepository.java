package com.nixmash.wp.migrator.db.local.repository;

import com.nixmash.wp.migrator.db.local.model.Authority;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByAuthority(String authority);
    Collection<Authority> findAll() throws DataAccessException;

}
