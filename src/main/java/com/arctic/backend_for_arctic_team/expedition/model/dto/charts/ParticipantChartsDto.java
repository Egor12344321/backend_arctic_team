package com.arctic.backend_for_arctic_team.expedition.model.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantChartsDto {
    private String indNum;
    private Long expeditionId;
    private List<ChartDto> charts;
}