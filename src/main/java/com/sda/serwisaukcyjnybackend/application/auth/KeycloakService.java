package com.sda.serwisaukcyjnybackend.application.auth;

import com.sda.serwisaukcyjnybackend.application.auth.exception.KeycloakCommunicationException;
import com.sda.serwisaukcyjnybackend.config.auth.keycloak.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    public String addUser(UserRepresentation userRepresentation) {
        Response response = keycloak.realm(keycloakProperties.getRealm()).users().create(userRepresentation);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new KeycloakCommunicationException();
        }
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    public void verifyUserEmail(String principalId) {
        var userResource = getUserResource(principalId);
        var userRepresentation =  userResource.toRepresentation();
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        getUserResource(principalId).update(userRepresentation);
    }

    public void logout(String principalId) {
        getUserResource(principalId).logout();
    }

    public void deleteAccount(String principalId) {
        getUserResource(principalId).remove();
    }

    private UserResource getUserResource(String principalId) {
        return keycloak.realm(keycloakProperties.getRealm()).users().get(principalId);
    }

}
