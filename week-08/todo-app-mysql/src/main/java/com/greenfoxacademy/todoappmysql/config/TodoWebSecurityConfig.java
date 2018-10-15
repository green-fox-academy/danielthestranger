package com.greenfoxacademy.todoappmysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class TodoWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(
                    "select username, password, enabled from user " +
                    "where username=?")
            .authoritiesByUsernameQuery(
                    "select username, authority from user_authority " +
                    "where username=?")
            .passwordEncoder(encoder())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/todo/**", "/assignee/**")
                    .hasAuthority("ROLE_USER")
                .antMatchers("/", "/**")
                    .permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/todo/")
        ;
    }
}
