package id.my.hendisantika.keycloaksample5.controller;

import id.my.hendisantika.keycloaksample5.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sample5
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/10/24
 * Time: 14.35
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;
}
