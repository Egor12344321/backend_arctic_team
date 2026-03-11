package com.arctic.backend_for_arctic_team.auth.config;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.entity.UserRole;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Profile("!prod")
@Slf4j
public class TestUserInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner createTestUsers() {
        return args -> {
            // создание аккаунта со всеми правами
            log.info("Start creating test users");
            if (!userRepository.existsByEmail("all@mail.ru")) {
                User superUser = User.builder()
                        .email("all@mail.ru")
                        .password(passwordEncoder.encode("111111"))
                        .firstName("All")
                        .lastName("Roles")
                        .roles(Set.of(
                                UserRole.ROLE_ADMIN,
                                UserRole.ROLE_LEADER,
                                UserRole.ROLE_USER
                        ))
                        .build();
                userRepository.save(superUser);
            }
            if (!userRepository.existsByEmail("leader@mail.ru")) {
                User leader = User.builder()
                        .email("leader@mail.ru")
                        .password(passwordEncoder.encode("111111"))
                        .firstName("Leader")
                        .lastName("Leader")
                        .roles(Set.of(
                                UserRole.ROLE_LEADER,
                                UserRole.ROLE_USER
                        ))
                        .build();
                userRepository.save(leader);
            }
            if (!userRepository.existsByEmail("user@mail.ru")){
                User user = User.builder()
                        .email("user@mail.ru")
                        .password(passwordEncoder.encode("111111"))
                        .firstName("Leader")
                        .lastName("Leader")
                        .roles(Set.of(
                                UserRole.ROLE_USER
                        ))
                        .build();
                userRepository.save(user);
            }

        };
    }


}
