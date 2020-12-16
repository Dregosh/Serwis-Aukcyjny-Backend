package com.sda.serwisaukcyjnybackend.application.auth.register;

import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateUserCommand;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepresentationMapper {

    public static UserRepresentation mapFromRegister(RegisterUserCommand registerUserCommand, Long userId) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(registerUserCommand.getEmail());
        userRepresentation.setEmail(registerUserCommand.getEmail());
        userRepresentation.setFirstName(registerUserCommand.getFirstName());
        userRepresentation.setLastName(registerUserCommand.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(new String(registerUserCommand.getPassword()));
        registerUserCommand.utilizePassword();

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("userId", List.of(String.valueOf(userId)));

        userRepresentation.setAttributes(attributes);

        return userRepresentation;
    }
}
