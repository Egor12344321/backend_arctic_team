package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;

import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.dto.response.admin_responses.UserWithRolesResponse;
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


    // Получение списка всех пользователей, зарегистрированных в системе
    public List<UserWithRolesResponse> getAllUsers(User user) {
        log.info("ADMIN-SERVICE: Admin getting all users: {}", user.getId());

        List<User> users = userRepository.findAllWithRoles();

        return users.stream()
                .map(userMapperService::mapToUserWithRolesResponse)
                .toList();
    }

    // Добавление роли админа пользотелю
    public UserWithRolesResponse promoteToAdmin(Long userId){
        return addRole(userId, UserRole.ROLE_ADMIN);
    }


    // Добавление роли лидера пользователю
    public UserWithRolesResponse promoteToLeader(Long userId){
        return addRole(userId, UserRole.ROLE_LEADER);
    };

    // Удаление роли админа у пользователя
    public UserWithRolesResponse deleteAdminRole(Long userId) {
        return deleteRole(userId, UserRole.ROLE_ADMIN);
    }

    // Удаление роли лидера у пользователя
    public UserWithRolesResponse deleteLeaderRole(Long userId) {
        return deleteRole(userId, UserRole.ROLE_LEADER);
    }

    private UserWithRolesResponse deleteRole(Long userId, UserRole role){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь " + userId + " не найден"));

        if (!user.getRoles().contains(UserRole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Пользователь не имеет роль " + role);
        }

        user.getRoles().remove(role);
        User savedUser = userRepository.save(user);
        return userMapperService.mapToUserWithRolesResponse(savedUser);
    }

    private UserWithRolesResponse addRole(Long userId, UserRole role){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь " + userId + " не найден"));

        if (user.getRoles().contains(role)) {
            throw new IllegalArgumentException("Пользователь уже имеет роль " + role);
        }
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return userMapperService.mapToUserWithRolesResponse(savedUser);
    }
}
