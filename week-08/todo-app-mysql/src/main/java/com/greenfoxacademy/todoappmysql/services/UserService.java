package com.greenfoxacademy.todoappmysql.services;


import com.greenfoxacademy.todoappmysql.models.security.Authority;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface UserService {
    static Authority getDefaultAuthority() {
        throw new NotImplementedException();
    };
}
