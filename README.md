# OAuth2 in Spring Boot Applications

**_Course_**

[Udemy](https://www.udemy.com/course/oauth2-in-spring-boot-applications/)

## Run Docker Keycloack

```
docker run -p 8081:8080 -d -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin1234 quay.io/keycloak/keycloak:20.0.2 start-dev
```

**__Modules__**

1. [Keycloak Resource Server](https://github.com/brunomilitzer/oauth2-spring-boot/tree/master/keycloak-resource-server)
2. [Spring Authorization Server](https://github.com/brunomilitzer/oauth2-spring-boot/tree/master/spring-authorization-server)
3. [Order Resource Server](https://github.com/brunomilitzer/oauth2-spring-boot/tree/master/order-resource-server)
4. [Order Web OAuth Client](https://github.com/brunomilitzer/oauth2-spring-boot/tree/master/orders-web-oauth-client)