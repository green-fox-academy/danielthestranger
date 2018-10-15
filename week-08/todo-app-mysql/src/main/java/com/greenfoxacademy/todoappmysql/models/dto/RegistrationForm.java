package com.greenfoxacademy.todoappmysql.models.dto;

import com.greenfoxacademy.todoappmysql.models.security.Authority;
import com.greenfoxacademy.todoappmysql.models.security.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationForm {
    private String username;
    private String password;

    public User toUser(PasswordEncoder encoder) {
        User user = new User(
                username,
                encoder.encode(password),
                true
        );
        user.addAuthority(new Authority("ROLE_USER"));

        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
