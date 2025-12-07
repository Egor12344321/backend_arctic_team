package com.arctic.backend_for_arctic_team.expedition.model.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateExpeditionRequest(
        @Size(min = 3, max = 100) String name,
        @Size(max = 500) String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
