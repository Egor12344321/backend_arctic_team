package com.arctic.backend_for_arctic_team.expedition.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddParticipantRequest(
        @NotBlank String individualNumber
) {
}
