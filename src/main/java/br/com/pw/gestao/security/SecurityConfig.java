package br.com.pw.gestao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityCompanyFilter securityCompanyFilter;
    private final SecurityCandidateFilter securityCandidateFilter;

    public SecurityConfig(
         SecurityCompanyFilter securityCompanyFilter,
         SecurityCandidateFilter securityCandidateFilter
    ) {
        this.securityCandidateFilter=securityCandidateFilter;
        this.securityCompanyFilter=securityCompanyFilter;
    }

    private static final String[] PERMIT_ALL_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/actuator/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->{
            auth.requestMatchers("/candidate/").permitAll()
            .requestMatchers("/company/").permitAll()
            .requestMatchers("/company/auth").permitAll()
            .requestMatchers("/candidate/auth").permitAll()
            .requestMatchers(PERMIT_ALL_LIST).permitAll()
            
            ;
            auth.anyRequest().authenticated();

        })
            .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}