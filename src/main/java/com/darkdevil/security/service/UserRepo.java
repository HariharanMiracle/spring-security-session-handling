package com.darkdevil.security.service;

import com.darkdevil.security.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<Users, String> {
}
