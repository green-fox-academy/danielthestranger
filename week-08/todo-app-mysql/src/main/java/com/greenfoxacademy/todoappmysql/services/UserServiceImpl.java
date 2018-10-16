package com.greenfoxacademy.todoappmysql.services;

import com.greenfoxacademy.todoappmysql.models.security.Authority;
import com.greenfoxacademy.todoappmysql.models.security.User;
import com.greenfoxacademy.todoappmysql.repositories.AuthorityRepository;
import com.greenfoxacademy.todoappmysql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private static final Authority defaultAuthority = new Authority("ROLE_USER");

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;

        createDefaultUsers();
    }

    public static Authority getDefaultAuthority() {
        return defaultAuthority;
    }


    private void createDefaultUsers() {
        User u1 = new User();
        u1.setUsername("dani");
        u1.setPassword(passwordEncoder.encode("test"));
        u1.setEnabled(true);
        u1.addAuthority(getDefaultAuthority());
        u1.addAuthority(new Authority("ROLE_ADMIN"));

        userRepository.save(u1);
    }
}
