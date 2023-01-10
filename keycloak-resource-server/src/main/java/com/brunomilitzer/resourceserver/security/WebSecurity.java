package com.brunomilitzer.resourceserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {


    @Bean
    public SecurityFilterChain securityFilterChain( final HttpSecurity http ) throws Exception {
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter( new KeycloackRoleConverter() );

        http.authorizeHttpRequests()
                .antMatchers( HttpMethod.GET, "/users/status/check" )
                //.hasAuthority( "SCOPE_profile" )
                .hasRole( "user" )
                .anyRequest().authenticated()
                .and().formLogin()
                .and().oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter( jwtAuthenticationConverter );

        return http.build();
    }

}
