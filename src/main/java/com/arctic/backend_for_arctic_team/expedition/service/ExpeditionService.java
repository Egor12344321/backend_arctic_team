package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.security.UserDetailsImpl;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.UpdateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpeditionService {
    private final ExpeditionRepository expeditionRepository;
    private final UserRepository userRepository;
    private final MapperService mapperService;

    public ExpeditionResponse createExpedition(@Valid CreateExpeditionRequest request, User currentUser) {
        User user = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден"));
        Expedition expedition = mapperService.mapFromRequestToEntity(request, user);
        expeditionRepository.save(expedition);
        log.info("Expedition saved successfully");
        return ExpeditionResponse.mapFromEntityToResponse(expedition);
    }

    public UserExpeditionResponse getUserExpeditions(UserDetailsImpl currentUser) {

    }

    public ExpeditionResponse updateExpedition(Long expeditionId, @Valid UpdateExpeditionRequest request, User currentUser) {
    }

    public void deleteExpedition(Long expeditionId, User currentUser) {
    }

    public ExpeditionResponse getExpeditionDetails(Long expeditionId, User currentUser) {
    }
}
