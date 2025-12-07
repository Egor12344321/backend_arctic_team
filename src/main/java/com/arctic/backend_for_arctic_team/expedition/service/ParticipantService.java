package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public List<ParticipantResponse> getExpeditionParticipants(Long expeditionId, User currentUser) {
    }

    public ParticipantResponse addParticipant(Long expeditionId, @Valid AddParticipantRequest request, User currentUser) {
    }

    public void removeParticipant(Long expeditionId, Long userId, User currentUser) {
    }
}
