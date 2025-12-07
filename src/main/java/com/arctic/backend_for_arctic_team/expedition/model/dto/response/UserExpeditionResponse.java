package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import java.util.List;

public record UserExpeditionResponse(
        List<ExpeditionResponse> asParticipant,
        List<ExpeditionResponse> asLeader
) {}