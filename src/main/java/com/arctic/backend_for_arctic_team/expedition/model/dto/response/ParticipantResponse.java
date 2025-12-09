package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;

import java.time.LocalDateTime;

public record ParticipantResponse(
        Long participantId,
        UserResponse user,
        LocalDateTime joinedAt,
        Long expeditionId) {
    public static ParticipantResponse mapFromEntityToResponse(Participant participant){
        return new ParticipantResponse(
                participant.getId(),
                UserResponse.mapFromEntityToResponse(participant.getUser()),
                participant.getJoinedAt(),
                participant.getExpedition().getId()
        );
    }
}
