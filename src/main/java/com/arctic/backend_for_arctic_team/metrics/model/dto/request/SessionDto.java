package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.SessionResults;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SessionDto(
        @JsonProperty("individualNumber") String individualNumber,
        @JsonProperty("expeditionId") String expeditionId,
        @JsonProperty("session") Integer session,
        @JsonProperty("objectiveCognitive") Integer objectiveCognitive,
        @JsonProperty("objectivePsychological") Integer objectivePsychological,
        @JsonProperty("objectivePhysiological") Integer objectivePhysiological,
        @JsonProperty("subjectiveCognitive") Integer subjectiveCognitive,
        @JsonProperty("subjectivePsychological") Integer subjectivePsychological,
        @JsonProperty("subjectivePhysiological") Integer subjectivePhysiological,
        @JsonProperty("totalIndex") Integer totalIndex,
        @JsonProperty("averageObjective") Integer averageObjective,
        @JsonProperty("averageSubjective") Integer averageSubjective,
        @JsonProperty("totalCognitive") Integer totalCognitive,
        @JsonProperty("totalPhysiological") Integer totalPhysiological,
        @JsonProperty("totalPsychological") Integer totalPsychological,
        @JsonProperty("durationMinutes") Integer durationMinutes,
        @JsonProperty("endTime") Integer endTime,
        @JsonProperty("sessionCategory") String sessionCategory,
        @JsonProperty("comment") String comment,
        @JsonProperty("objectiveFatigue") String objectiveFatigue,
        @JsonProperty("objectiveStress") String objectiveStress,
        @JsonProperty("passingPrematurely") Boolean passingPrematurely
) {
    public static SessionResults toEntity(SessionDto dto) {
        return SessionResults.builder()
                .individualNumber(dto.individualNumber())
                .expeditionId(Long.valueOf(dto.expeditionId()))
                .session(dto.session())
                .objectiveCognitive(dto.objectiveCognitive())
                .objectivePsychological(dto.objectivePsychological())
                .objectivePhysiological(dto.objectivePhysiological())
                .subjectiveCognitive(dto.subjectiveCognitive())
                .subjectivePsychological(dto.subjectivePsychological())
                .subjectivePhysiological(dto.subjectivePhysiological())
                .totalIndex(dto.totalIndex())
                .averageObjective(dto.averageObjective())
                .averageSubjective(dto.averageSubjective())
                .totalCognitive(dto.totalCognitive())
                .totalPhysiological(dto.totalPhysiological())
                .totalPsychological(dto.totalPsychological())
                .durationMinutes(dto.durationMinutes())
                .endTime(dto.endTime())
                .sessionCategory(dto.sessionCategory())
                .comment(dto.comment())
                .objectiveFatigue(dto.objectiveFatigue())
                .objectiveStress(dto.objectiveStress())
                .passingPrematurely(dto.passingPrematurely())
                .build();
    }
}