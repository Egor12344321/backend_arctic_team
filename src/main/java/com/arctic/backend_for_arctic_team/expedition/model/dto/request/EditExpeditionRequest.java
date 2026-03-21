package com.arctic.backend_for_arctic_team.expedition.model.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EditExpeditionRequest(
        String name,
        @Size(max = 500) String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
