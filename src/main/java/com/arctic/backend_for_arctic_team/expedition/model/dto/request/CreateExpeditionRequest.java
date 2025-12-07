package com.arctic.backend_for_arctic_team.expedition.model.dto.request;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateExpeditionRequest(
        @NotBlank @Size(min = 3, max = 100) String name,
        @Size(max = 500) String description,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull @Future LocalDate endDate
) {
}
