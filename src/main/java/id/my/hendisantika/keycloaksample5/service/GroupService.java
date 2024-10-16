package id.my.hendisantika.keycloaksample5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sample5
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/10/24
 * Time: 14.27
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final Keycloak keycloak;

    private final UserService userService;

    @Value("${app.keycloak.realm}")
    private String realm;

    public void assignGroup(String userId, String groupId) {
        UserResource user = userService.getUser(userId);
        user.joinGroup(groupId);
        log.info("User joined group {}", groupId);
    }
}
