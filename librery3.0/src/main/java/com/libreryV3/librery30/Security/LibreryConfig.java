package com.libreryV3.librery30.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LibreryConfig {

    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder();
    }
}
