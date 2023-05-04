package com.example.springgumball.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Queue gumball() {
        return new Queue("gumball");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/access-denied").setViewName("access-denied");
        registry.addViewController("/about").setViewName("about-us");
    }
}


