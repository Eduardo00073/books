package br.com.eduardogabriel.books.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("http://localhost:5173", // Adicione as URLs necessárias
                        "http://www.fatecjales.edu.br",
                        "http://www.bookregistration.com.br")
                .allowedHeaders("*")
                .allowCredentials(true); // Se necessário para suas requisições
    }
}
