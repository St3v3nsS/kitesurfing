package com.intership.kitesurfing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api/home").setViewName("home");
        registry.addViewController("/api/").setViewName("home");
        registry.addViewController("/api/users/me").setViewName("dashboard");
        registry.addViewController("/api/login").setViewName("login");
        registry.addViewController("/api/signup").setViewName("signup");
        registry.addViewController("/api/favorites/spots").setViewName("favorites");
    }
}
