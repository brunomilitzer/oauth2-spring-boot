package com.brunomilitzer.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {
        http.authorizeHttpRequests( auth -> auth.anyRequest().authenticated() ).formLogin( withDefaults() );

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final UserDetails user = User.withUsername( "bmilitzer" ).password( encoder.encode( "password" ) )
                .roles( "USER" ).build();

        return new InMemoryUserDetailsManager( user );
    }

}
