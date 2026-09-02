package com.usuarios.service;

import com.usuarios.dto.UserRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyCloackService {

    private final Keycloak keycloak;

    @Value(value = "${keycloack.realm}")
    private String realm;

    @Value(value = "${keycloack.clientId}")
    private String clientId;

//    public void createUser(UserRequest request) {
//        UsersResource users = keycloak.realm(realm).users();
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setFirstName(request.getFirstname());
//        user.setLastName(request.getLastname());
//        user.setEmailVerified(true);
//        user.setEnabled(true);
//
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setValue(request.getPassword());
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//        credentialRepresentation.setTemporary(false);
//
//        List<CredentialRepresentation> credentials = new ArrayList<>();
//        credentials.add(credentialRepresentation);
//
//        user.setCredentials(credentials);
//
//        Response response = users.create(user);
//
//        String createdId = CreatedResponseUtil.getCreatedId(response);
//
//        List<ClientRepresentation> byClientId = keycloak.realm(realm).clients().findByClientId(clientId);
//        String id = byClientId.get(0).getId();
//        RoleRepresentation role = keycloak.realm(realm).clients().get(id).roles().get("USER").toRepresentation();
//        users.get(createdId).roles().clientLevel(id).add(Collections.singletonList(role));
//    }


    public void createUser(UserRequest request) {
        UsersResource users = keycloak.realm(realm).users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmailVerified(true);
        user.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);

        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credentialRepresentation);

        user.setCredentials(credentials);

        Response response = users.create(user);

        String createdId = CreatedResponseUtil.getCreatedId(response);

        List<ClientRepresentation> byClientId = keycloak.realm(realm).clients().findByClientId(clientId);
        String id = byClientId.get(0).getId();
        RoleRepresentation role = keycloak.realm(realm).clients().get(id).roles().get("USER").toRepresentation();
        users.get(createdId).roles().clientLevel(id).add(Collections.singletonList(role));
    }

    public void createAdmin(UserRequest request) {
        UsersResource users = keycloak.realm(realm).users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmailVerified(true);
        user.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);

        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credentialRepresentation);

        user.setCredentials(credentials);

        Response response = users.create(user);

        String createdId = CreatedResponseUtil.getCreatedId(response);

        List<ClientRepresentation> byClientId = keycloak.realm(realm).clients().findByClientId(clientId);
        String id = byClientId.get(0).getId();
        RoleRepresentation role = keycloak.realm(realm).clients().get(id).roles().get("ADMIN").toRepresentation();
        users.get(createdId).roles().clientLevel(id).add(Collections.singletonList(role));
    }
}
