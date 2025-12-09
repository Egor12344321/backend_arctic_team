package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpeditionResponse(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        String role,
        LocalDateTime createdAt,
        String leaderLastName,
        String leaderFirstName,
        String leaderEmail

) {
    public static ExpeditionResponse mapFromEntityToResponse(Expedition expedition){
        return new ExpeditionResponse(
            expedition.getId(),
            expedition.getName(),
            expedition.getStartDate(),
            expedition.getEndDate(),
                null,
                expedition.getCreatedAt(),
                expedition.getLeader().getLastName(),
                expedition.getLeader().getFirstName(),
                expedition.getLeader().getEmail()
        );
    }
    public static ExpeditionResponse forLeader(Expedition expedition) {
        return new ExpeditionResponse(
                expedition.getId(),
                expedition.getName(),
                expedition.getStartDate(),
                expedition.getEndDate(),
                "LEADER",
                expedition.getCreatedAt(),
                expedition.getLeader().getLastName(),
                expedition.getLeader().getFirstName(),
                expedition.getLeader().getEmail()
        );
    }

    public static ExpeditionResponse forParticipant(Expedition expedition) {
        return new ExpeditionResponse(
                expedition.getId(),
                expedition.getName(),
                expedition.getStartDate(),
                expedition.getEndDate(),
                "PARTICIPANT",
                expedition.getCreatedAt(),
                expedition.getLeader().getLastName(),
                expedition.getLeader().getFirstName(),
                expedition.getLeader().getEmail()
        );
    }
}
