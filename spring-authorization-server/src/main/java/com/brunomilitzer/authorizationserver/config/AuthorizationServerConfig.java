package com.brunomilitzer.authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    public RegisteredClientRepository registerClientRepository() {
        final RegisteredClient registeredClient = RegisteredClient.withId( UUID.randomUUID().toString() )
                .clientId( "client1" ).clientSecret( "{noop}myClientSecretValue" )
                .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
                .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
                .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                .redirectUri( "http://localhost:8080/login/oauth2/code/users-client-oidc" )
                .redirectUri( "http://localhost:8080/authorized" )
                .scope( OidcScopes.OPENID )
                .scope( "read" )
                //.clientSettings( ClientSettings.builder().requireAuthorizationConsent( true ).build() )
                .build();

        return new InMemoryRegisteredClientRepository( registeredClient );
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain( final HttpSecurity http ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity( http );

        return http.formLogin( withDefaults() ).build();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer( "http://auth-server:8000" ).build();
    }

    @Bean
    public JwtDecoder jwtDecoder( final JWKSource<SecurityContext> jwkSource ) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder( jwkSource );
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        final RSAKey rsaKey = this.generateRsa();
        final JWKSet jwkSet = new JWKSet( rsaKey );

        return ( jwkSelector, securityContext ) -> jwkSelector.select( jwkSet );
    }

    private RSAKey generateRsa() throws NoSuchAlgorithmException {
        final KeyPair keyPair = this.generateRsaKey();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder( publicKey ).privateKey( privateKey ).keyID( UUID.randomUUID().toString() ).build();
    }

    private KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( 2048 );

        return keyPairGenerator.generateKeyPair();
    }

}
