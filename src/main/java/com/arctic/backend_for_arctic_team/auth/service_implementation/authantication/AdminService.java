package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;

import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.dto.reponse.admin_responses.UserWithRolesResponse;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.entity.UserRole;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    public List<UserWithRolesResponse> getAllUsers(User user) {
        log.info("ADMIN-SERVICE: Admin getting all users: {}", user.getId());

        List<User> users = userRepository.findAllWithRoles();

        return users.stream()
                .map(userMapperService::mapToUserWithRolesResponse)
                .toList();
    }

    public UserWithRolesResponse promoteToAdmin(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь " + userId + " не найден"));

        user.getRoles().add(UserRole.ROLE_ADMIN);

        User saved_user = userRepository.save(user);
        return userMapperService.mapToUserWithRolesResponse(saved_user);
    }

    public UserWithRolesResponse promoteToLeader(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь " + userId + " не найден"));

        if (user.getRoles().contains(UserRole.ROLE_LEADER)) {
            throw new IllegalArgumentException("Пользователь уже имеет роль LEADER");
        }
        user.getRoles().add(UserRole.ROLE_LEADER);
        User saved_user = userRepository.save(user);
        return userMapperService.mapToUserWithRolesResponse(saved_user);
    };
}
