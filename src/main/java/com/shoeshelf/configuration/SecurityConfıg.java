package com.shoeshelf.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.annotation.Validated;

/* OBasic Security Burda Set edilri Spring Securtiy@den yardim aliniri */

@Configuration
@EnableWebSecurity
public class SecurityConfıg {


    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/actuator/health"
    };

    @Bean
    @ConfigurationProperties(prefix = "security.auth")
    public AuthProperties authProperties() {
        return new AuthProperties();
    }


    /* Cors ayarlari burda yapiliyor */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers("/api/user/check-user").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider(userDetailsService));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /* Applicaton.yml dosyasindaki username passwodr burda set ediliyor */
    @Bean
    UserDetailsService userDetailsService(AuthProperties authProperties) {
        UserDetails user = User.withUsername(authProperties.getUser())
                .password(passwordEncoder().encode(authProperties.getPassword()))
                .authorities(authProperties.getRole())
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



