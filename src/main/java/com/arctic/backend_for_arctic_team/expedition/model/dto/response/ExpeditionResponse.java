package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;

import java.time.LocalDate;

public record ExpeditionResponse(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate) {
    public static ExpeditionResponse mapFromEntityToResponse(Expedition expedition){
        return new ExpeditionResponse(
            expedition.getId(),
            expedition.getName(),
            expedition.getStartDate(),
            expedition.getEndDate()
        );
    }
}
