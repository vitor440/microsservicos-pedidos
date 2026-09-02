package com.usuarios.configuration;

import org.keycloak.AuthorizationContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloackConfiguration {

    @Value(value = "${keycloack.host}")
    private String host;

    @Value(value = "${keycloack.realmMaster}")
    private String realm;

    @Value(value = "${keycloack.username}")
    private String username;

    @Value(value = "${keycloack.password}")
    private String password;


    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(host)
                .realm(realm)
                .clientId("admin-cli")
                .grantType("password")
                .username(username)
                .password(password)
                .build();
    }
}
