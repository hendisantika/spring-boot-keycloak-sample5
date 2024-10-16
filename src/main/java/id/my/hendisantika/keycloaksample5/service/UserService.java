package id.my.hendisantika.keycloaksample5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
