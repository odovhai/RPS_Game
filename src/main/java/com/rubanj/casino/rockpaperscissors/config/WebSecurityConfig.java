package com.rubanj.casino.rockpaperscissors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.admin-user}")
    private String adminUser;
    @Value("${app.admin-password}")
    private String adminPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/games/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").hasRole(Roles.ADMIN.toString());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser(adminUser)
                .password(passwordEncoder().encode(adminPassword))
                .roles(Roles.ADMIN.toString());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private enum Roles {
        ADMIN
    }
}
