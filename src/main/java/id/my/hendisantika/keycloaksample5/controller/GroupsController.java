package id.my.hendisantika.keycloaksample5.controller;

import id.my.hendisantika.keycloaksample5.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sample5
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/10/24
 * Time: 14.36
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {
    private final GroupService groupService;

    @PutMapping("/{groupId}/assign/users/{userId}")
    public ResponseEntity<?> assignGroup(@PathVariable String userId, @PathVariable String groupId) {
        groupService.assignGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{groupId}/remove/users/{userId}")
    public ResponseEntity<?> unAssignGroup(@PathVariable String userId, @PathVariable String groupId) {
        groupService.deleteGroupFromUser(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
