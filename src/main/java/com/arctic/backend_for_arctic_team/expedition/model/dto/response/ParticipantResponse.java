package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import java.time.LocalDateTime;

public record ParticipantResponse(
        UserResponse user,
        LocalDateTime joinedAt,
        Integer metricsCount,
        LocalDateTime lastMetricTime
) {
}
