package com.arctic.backend_for_arctic_team.expedition.model.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChartDto {
    private String chartType;
    private byte[] image;
}