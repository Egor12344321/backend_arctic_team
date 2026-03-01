package com.arctic.backend_for_arctic_team.auth.controller;


import com.arctic.backend_for_arctic_team.auth.dto.reponse.user_responses.UserSearchResponse;
import com.arctic.backend_for_arctic_team.auth.service_interface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/by-individual-number/{individual-number}")
    @Operation(summary = "Получение пользователя по индвидуальному номеру")
    public ResponseEntity<UserSearchResponse> findUserByIndividualNumber(@PathVariable("individual-number") String individualNumber){
        log.debug("USER-CONTROLLER: Starting searching user with individual number: {}", individualNumber);
        UserSearchResponse userSearchResponse = userService.searchUserByIndividualNumber(individualNumber);
        log.debug("USER-CONTROLLER: Ended searching user with individual number: {}", individualNumber);
        return ResponseEntity.ok(userSearchResponse);
    }
}
