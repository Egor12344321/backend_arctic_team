package com.arctic.backend_for_arctic_team.auth.controller;

import com.arctic.backend_for_arctic_team.auth.dto.reponse.admin_responses.UserWithRolesResponse;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.entity.UserRole;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.service_implementation.authantication.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    @Operation(summary = "Получение всех пользователей")
    public ResponseEntity<List<UserWithRolesResponse>> getAllUsers(
            @AuthenticationPrincipal User currentAdmin) {
        log.debug("ADMIN-CONTROLLER: Admin started getting all users");
        List<UserWithRolesResponse> responses = adminService.getAllUsers(currentAdmin);
        log.debug("ADMIN-CONTROLLER: Admin ended getting all users");
        return ResponseEntity.ok(responses);
    }


    @PatchMapping("/{userId}/roles/admin")
    @Operation(summary = "Добавление роли админа пользователю")
    public ResponseEntity<UserWithRolesResponse> promoteToAdmin(
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentAdmin) {

        log.debug("ADMIN-CONTROLLER: Admin started promoting admin role to user: {}", userId);
        UserWithRolesResponse userWithRolesResponse = adminService.promoteToAdmin(userId);
        log.debug("ADMIN-CONTROLLER: Admin ended promoting admin role to user: {}", userId);

        return ResponseEntity.ok(userWithRolesResponse);
    }

    @PatchMapping("/{userId}/roles/leader")
    @Operation(summary = "Добавление роли лидера пользователю")
    public ResponseEntity<UserWithRolesResponse> promoteToLeader(
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentAdmin) {


        log.debug("ADMIN-CONTROLLER: Admin started promoting leader role to user: {}", userId);
        UserWithRolesResponse userWithRolesResponse = adminService.promoteToLeader(userId);
        log.debug("ADMIN-CONTROLLER: Admin ended promoting leader role to user: {}", userId);

        return ResponseEntity.ok(userWithRolesResponse);
    }


    @DeleteMapping("/{userId}/roles/admin")
    @Operation(summary = "Удаление роли админа у пользователя")
    public ResponseEntity<UserWithRolesResponse> deleteAdminRole(@PathVariable Long userId, @AuthenticationPrincipal User currentAdmin){
        log.debug("ADMIN-CONTROLLER: Admin {} started deleting admin role from user: {}", currentAdmin.getId(), userId);
        UserWithRolesResponse response = adminService.deleteAdminRole(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/roles/leader")
    @Operation(summary = "Удаление роли лидера у пользователя")
    public ResponseEntity<UserWithRolesResponse> deleteLeaderRole(@PathVariable Long userId, @AuthenticationPrincipal User currentAdmin){
        log.debug("ADMIN-CONTROLLER: Admin {} started deleting leader role from user: {}", currentAdmin.getId(), userId);
        UserWithRolesResponse response = adminService.deleteLeaderRole(userId);
        return ResponseEntity.ok(response);
    }









}