package com.greenfoxacademy.todoappmysql.services;

import com.greenfoxacademy.todoappmysql.models.security.Authority;
import com.greenfoxacademy.todoappmysql.models.security.User;
import com.greenfoxacademy.todoappmysql.repositories.AuthorityRepository;
import com.greenfoxacademy.todoappmysql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;

        createDefaultUsers();
    }

    private void createDefaultUsers() {
        Authority auth = createDefaultAuthority("ROLE_USER");

        User u1 = new User();
        u1.setUsername("dani");
        u1.setPassword("test");
        u1.setEnabled(true);
        u1.addAuthority(auth);

        userRepository.save(u1);
    }

    private Authority createDefaultAuthority(String authorityDesc) {
        Authority a1 = new Authority(authorityDesc);
        return authorityRepository.save(a1);
    }
}
