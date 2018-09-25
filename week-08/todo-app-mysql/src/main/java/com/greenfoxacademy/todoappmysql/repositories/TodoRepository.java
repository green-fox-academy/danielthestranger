package com.greenfoxacademy.todoappmysql.repositories;

import com.greenfoxacademy.todoappmysql.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
