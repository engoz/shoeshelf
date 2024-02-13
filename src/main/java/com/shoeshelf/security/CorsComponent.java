package com.shoeshelf.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@Component
@ConfigurationProperties(prefix = "cors")
@Getter
@Setter
public class CorsComponent {
    private CorsConfiguration config;
}
