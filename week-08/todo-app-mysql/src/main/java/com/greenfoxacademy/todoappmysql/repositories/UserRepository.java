package com.greenfoxacademy.todoappmysql.repositories;

import com.greenfoxacademy.todoappmysql.models.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
