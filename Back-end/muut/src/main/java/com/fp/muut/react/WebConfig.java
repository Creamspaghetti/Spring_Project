package com.fp.muut.react;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5173")
                .allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE");
                
                // 김영범 테스트용입니다.
//                registry.addMapping("/**").allowedOrigins("http://localhost:5174")
//                .allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
	}

}
