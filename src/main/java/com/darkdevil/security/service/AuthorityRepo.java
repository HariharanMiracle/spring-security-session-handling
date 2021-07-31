package com.darkdevil.security.service;

import com.darkdevil.security.model.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends CrudRepository<Authorities, String> {
}
