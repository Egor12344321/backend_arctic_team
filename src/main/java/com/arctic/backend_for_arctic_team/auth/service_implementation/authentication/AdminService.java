package com.arctic.backend_for_arctic_team.auth.service_implementation.authentication;

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

        List<User> users = userRepository.findAllWithRoles();
        log.info("Админ {} получил всех пользователей", user.getEmail());
        return users.stream()
                .map(userMapperService::mapToUserWithRolesResponse)
                .toList();
    }

    // Добавление роли админа пользотелю
    public UserWithRolesResponse promoteToAdmin(Long userId, User admin){
        log.info("Админ: {} (id: {}) пытается добавить роль админа пользователю: {}", admin.getEmail(), admin.getId(), userId);
        return addRole(userId, UserRole.ROLE_ADMIN);
    }


    // Добавление роли лидера пользователю
    public UserWithRolesResponse promoteToLeader(Long userId, User admin){
        log.info("Админ: {} (id: {}) пытается добавить роль лидера пользователю: {}", admin.getEmail(), admin.getId(), userId);
        return addRole(userId, UserRole.ROLE_LEADER);
    };

    // Удаление роли админа у пользователя
    public UserWithRolesResponse deleteAdminRole(Long userId, User admin) {
        log.info("Админ: {} (id: {}) пытается удалить роль админа у пользователя: {}", admin.getEmail(), admin.getId(), userId);
        return deleteRole(userId, UserRole.ROLE_ADMIN);
    }

    // Удаление роли лидера у пользователя
    public UserWithRolesResponse deleteLeaderRole(Long userId, User admin) {
        log.info("Админ: {} (id: {}) пытается удалить роль лидера у пользователя: {}", admin.getEmail(), admin.getId(), userId);
        return deleteRole(userId, UserRole.ROLE_LEADER);
    }

    private UserWithRolesResponse deleteRole(Long userId, UserRole role){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь " + userId + " не найден"));

        if (!user.getRoles().contains(role)) {
            log.error("Пользователь не имеет роль {}", role);
            throw new IllegalArgumentException("Пользователь не имеет роль " + role);
        }

        user.getRoles().remove(role);
        User savedUser = userRepository.save(user);
        log.info("Админ удалил роль: {} у пользователя: {}", role, userId);
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
        log.info("Админ добавил роль: {} пользователю: {}", role, userId);
        return userMapperService.mapToUserWithRolesResponse(savedUser);
    }
}
