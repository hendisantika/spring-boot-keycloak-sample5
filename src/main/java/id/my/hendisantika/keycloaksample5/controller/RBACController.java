package id.my.hendisantika.keycloaksample5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sample5
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/10/24
 * Time: 14.37
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/rbac")
@RequiredArgsConstructor
public class RBACController {
    @GetMapping("merge-role")
    @PreAuthorize("hasAnyRole('MERGE')")
    public ResponseEntity<?> testForMergeRole() {
        log.info("SOMETHING ");
        return ResponseEntity.status(HttpStatus.OK).body("I HAVE ACCESS to MERGE ROLE");
    }
}
