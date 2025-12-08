package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;

import java.time.LocalDateTime;

public record ParticipantResponse(
        UserResponse user,
        LocalDateTime joinedAt) {
    public static ParticipantResponse mapFromEntityToResponse(Participant participant){
        return new ParticipantResponse(
                UserResponse.mapFromEntityToResponse(participant.getUser()),
                participant.getJoinedAt()
        );
    }
}
