package id.my.hendisantika.keycloaksample5.service;

import id.my.hendisantika.keycloaksample5.model.NewUserRecord;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sample5
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/10/24
 * Time: 14.16
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;

    public void createUser(NewUserRecord newUserRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.lastName());
        userRepresentation.setUsername(newUserRecord.username());
        userRepresentation.setEmail(newUserRecord.username());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(userRepresentation);

        log.info("Status Code " + response.getStatus());

        if (!Objects.equals(201, response.getStatus())) {

            throw new RuntimeException("Status code " + response.getStatus());
        }

        log.info("New user has been created");

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserRecord.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());
    }

    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
        log.info("Verification email has been sent {}", userId);
    }

    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
        log.info("User has been deleted");
    }

    public void forgotPassword(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        UserResource userResource = usersResource.get(userRepresentation1.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
        log.info("Forgot password has been sent {}", username);
    }

    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        log.info("Get User ...");
        return usersResource.get(userId);
    }

    public List<RoleRepresentation> getUserRoles(String userId) {
        log.info("Get user roles ...");
        return getUser(userId).roles().realmLevel().listAll();
    }

    public List<GroupRepresentation> getUserGroups(String userId) {
        log.info("Get user groups ...");
        return getUser(userId).groups();
    }

}
