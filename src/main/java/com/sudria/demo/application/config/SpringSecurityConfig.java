package com.sudria.demo.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN")
                .and()
                .withUser("esme").password("{noop}password").roles("USER", "ADMIN","ESME");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/cars/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/cars").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/cars/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/v1/cars/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/cars/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
