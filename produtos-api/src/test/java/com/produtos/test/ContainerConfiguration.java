package com.produtos.test;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

@Testcontainers
@TestConfiguration
public class ContainerConfiguration {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");


    @Container
    static KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:26.7.2")
            .withAdminUsername("admin")
            .withAdminPassword("admin123")
            .withRealmImportFile("/realm/produtos-realm-realm.json");


    @DynamicPropertySource
    static void propertySources(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("keycloack.clientId", () -> "client123");
        registry.add("keycloak.port", () -> keycloakContainer.getHttpPort());
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", () ->
                keycloakContainer.getAuthServerUrl() + "/realms/produtos-realm/protocol/openid-connect/certs");
    }
}
