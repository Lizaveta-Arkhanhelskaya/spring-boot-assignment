package com.arkhanhelskaya.freelancer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    public static final String STAFF_ROLE = "hasRole('STAFF')";
    public static final String FREELANCER_ROLE = "hasRole('FREELANCER')";

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/index.html",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("FREELANCER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("STAFF")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    public static String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
