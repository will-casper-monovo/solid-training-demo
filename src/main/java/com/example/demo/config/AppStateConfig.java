package com.example.demo.config;

import com.example.demo.model.AppState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStateConfig {

    @Bean
    public AppState appState() {
        return new AppState();
    }
}
