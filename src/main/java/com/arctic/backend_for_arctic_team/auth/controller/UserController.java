package com.arctic.backend_for_arctic_team.auth.controller;


import com.arctic.backend_for_arctic_team.auth.dto.response.user_responses.UserSearchResponse;
import com.arctic.backend_for_arctic_team.auth.service_interface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/search/by-individual-number/{individualNumber}")
    @PreAuthorize("hasAnyRole('LEADER', 'ADMIN')")
    @Operation(summary = "Получение пользователя по индивидуальному номеру")
    public ResponseEntity<UserSearchResponse> findUserByIndividualNumber(@PathVariable("individualNumber") String individualNumber){
        log.debug("USER-CONTROLLER: Starting searching user with individual number: {}", individualNumber);
        UserSearchResponse userSearchResponse = userService.searchUserByIndividualNumber(individualNumber);
        log.debug("USER-CONTROLLER: Ended searching user with individual number: {}", individualNumber);
        return ResponseEntity.ok(userSearchResponse);
    }
}
