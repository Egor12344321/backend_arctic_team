package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public Expedition mapFromRequestToEntity(@Valid CreateExpeditionRequest request, User user) {
        return Expedition.builder()
                .name(request.name())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .leader(user)
                .build();
    }


}
