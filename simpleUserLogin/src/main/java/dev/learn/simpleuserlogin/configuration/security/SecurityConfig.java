package dev.learn.simpleuserlogin.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider,
                          CorsConfigurationSource corsConfigurationSource
    ){
        this.authenticationProvider = authenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                config -> config
                        .requestMatchers("/api/public/**", "/api/auth/**").permitAll()
                        .requestMatchers("/api/user/**").hasRole("USER")
        );
        http.cors(config -> config.configurationSource(corsConfigurationSource));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
