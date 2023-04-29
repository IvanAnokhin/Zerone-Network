package ru.skillbox.zerone.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.skillbox.zerone.backend.security.UserAccessFilter;
import ru.skillbox.zerone.backend.security.FilterChainExceptionHandler;
import ru.skillbox.zerone.backend.security.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
  private static final String LOGOUT_ENDPOINT = "/api/v1/auth/logout";
  private final JwtTokenFilter jwtFilter;
  private final UserAccessFilter userAccessFilter;

  @Bean
  public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
      }
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         FilterChainExceptionHandler filterChainExceptionHandler) throws Exception {
    return http
        .cors(SecurityConfigurerAdapter::and)
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractAuthenticationFilterConfigurer::disable)
        .logout(LogoutConfigurer::permitAll)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(
                "/api/v1/account/register/confirm",
                "/api/v1/account/registration_complete",
                "/api/v1/account/register",
                "/api/v1/support",
                "/actuator/prometheus",
                "/api/v1/platform/languages",
                "/changeemail/complete",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger"
            ).permitAll()
            .requestMatchers(LOGIN_ENDPOINT).permitAll()
            .requestMatchers(LOGOUT_ENDPOINT).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(filterChainExceptionHandler, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(userAccessFilter, JwtTokenFilter.class)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(AbstractHttpConfigurer::disable)
        .build();
  }
}
