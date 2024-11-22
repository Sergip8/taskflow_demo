package com.taskflow.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@EnableAsync
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Component
	public class CustomCorsConfiguration implements CorsConfigurationSource {
		@Override
		public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
			config.setAllowedHeaders(List.of("*"));
			return config;
		}
	}
}
