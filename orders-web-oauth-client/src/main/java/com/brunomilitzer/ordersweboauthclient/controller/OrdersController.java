package com.brunomilitzer.ordersweboauthclient.controller;

import java.util.ArrayList;
import java.util.List;

import com.brunomilitzer.ordersweboauthclient.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class OrdersController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/orders")
    public String getOrders(
            Model model,
            @RegisteredOAuth2AuthorizedClient("users-client-oidc") OAuth2AuthorizedClient auth2AuthorizedClient ) {

        String jwtAccessToken = auth2AuthorizedClient.getAccessToken().getTokenValue();

        System.out.println( "jwtAccessToken = " + jwtAccessToken );

        String url = "http://localhost:8082/orders";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( "Authorization", "Bearer " + jwtAccessToken );

        HttpEntity<String> entity = new HttpEntity<>( httpHeaders );
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange( url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Order>>() { } );

        List<Order> orders = responseEntity.getBody();

        model.addAttribute( "orders", orders );

        return "orders-page";

    }

}
