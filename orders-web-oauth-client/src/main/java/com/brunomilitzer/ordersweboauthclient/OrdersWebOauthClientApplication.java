package com.brunomilitzer.ordersweboauthclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrdersWebOauthClientApplication {

    public static void main( String[] args ) {
        SpringApplication.run( OrdersWebOauthClientApplication.class, args );
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
