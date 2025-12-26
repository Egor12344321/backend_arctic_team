package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;


import com.arctic.backend_for_arctic_team.auth.dto.reponse.user_responses.UserSearchResponse;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.service_interface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserSearchResponse searchUserByIndividualNumber(String individualNumber) {
        User user = userRepository.findByIndividualNumber(individualNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с таким индивидуальным номером нет"));
        log.info("User with individual number fount: {}", individualNumber);
        return UserSearchResponse.fromUser(user);
    }
}

