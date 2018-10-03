package com.greenfoxacademy.todoappmysql.repositories;

import com.greenfoxacademy.todoappmysql.models.Assignee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends CrudRepository<Assignee, Long> {
}
