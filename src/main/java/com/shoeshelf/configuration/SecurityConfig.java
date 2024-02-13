package com.shoeshelf.configuration;

import com.shoeshelf.security.CorsComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final String API = "/api";
    private final CorsComponent corsComponent;

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password("{noop}secret1")
                .authorities("read")
                .roles("USER")
                .build();
        UserDetails userOne = User.withUsername("admin1")
                .password("{noop}secret1")
                .authorities("read,write")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, userOne);
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.anyRequest().permitAll());
        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {

        final var config = corsComponent.getConfig();

        final var source = new UrlBasedCorsConfigurationSource();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())) {
            source.registerCorsConfiguration(API, config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/topic/**", config);
            source.registerCorsConfiguration("/websocket/**", config);
            source.registerCorsConfiguration("/rest/**", config);
            source.registerCorsConfiguration("/actuator/info", config);
        }
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
