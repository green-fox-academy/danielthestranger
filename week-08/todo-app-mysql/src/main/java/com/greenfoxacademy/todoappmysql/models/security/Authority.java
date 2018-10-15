package com.greenfoxacademy.todoappmysql.models.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Authority {
    @Id
    @Column(length = 100)
    private String authority;
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
