package com.arctic.backend_for_arctic_team.metrics;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MappingMetricsDtoTests {
    @Test
    void testCardioMetricCompressedDto() {
        CardioMetricCompressedDto dto = new CardioMetricCompressedDto(
                "individualNumber",
                1L,
                "123",
                1,
                60.0,
                1,
                0.5,
                1,
                0,
                1,
                0.3
        );

        var result = CardioMetricCompressedDto.mapToCardioEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testCardioMetricDto() {
        CardioMetricDto dto = new CardioMetricDto(
                "individualNumber",
                1L,
                "123",
                1,
                60.0,
                1,
                0.5,
                1,
                0,
                1,
                0.3
        );

        var result = CardioMetricDto.mapToCardioEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGArtifactsMetricCompressedDto() {
        EEGArtifactsMetricCompressedDto dto = new EEGArtifactsMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                1,
                0,
                1,
                0
        );

        var result = EEGArtifactsMetricCompressedDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGArtifactsMetricDto() {
        EEGArtifactsMetricDto dto = new EEGArtifactsMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                1,
                0,
                1,
                0
        );

        var result = EEGArtifactsMetricDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGProceedMetricCompressedDto() {
        EEGProceedMetricCompressedDto dto = new EEGProceedMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5f,
                0.6f
        );

        var result = EEGProceedMetricCompressedDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGProceedMetricDto() {
        EEGProceedMetricDto dto = new EEGProceedMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5f,
                0.6f
        );

        var result = EEGProceedMetricDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGRawMetricCompressedDto() {
        EEGRawMetricCompressedDto dto = new EEGRawMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5f,
                0.6f
        );

        var result = EEGRawMetricCompressedDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEEGRawMetricDto() {
        EEGRawMetricDto dto = new EEGRawMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5f,
                0.6f
        );

        var result = EEGRawMetricDto.mapFromRequestToEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEmotionalMetricCompressedDto() {
        EmotionalMetricCompressedDto dto = new EmotionalMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5,
                0.6,
                0.7,
                0.8,
                0.9
        );

        var result = EmotionalMetricCompressedDto.mapToEmotionalEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testEmotionalMetricDto() {
        EmotionalMetricDto dto = new EmotionalMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.5,
                0.6,
                0.7,
                0.8,
                0.9
        );

        var result = EmotionalMetricDto.mapToEmotionalEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testMemsMetricCompressedDto() {
        MemsMetricCompressedDto dto = new MemsMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0
        );

        var result = MemsMetricCompressedDto.mapToMemsEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testMemsMetricDto() {
        MemsMetricDto dto = new MemsMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0
        );

        var result = MemsMetricDto.mapToMemsEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testNfbMetricCompressedDto() {
        NfbMetricCompressedDto dto = new NfbMetricCompressedDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        var result = NfbMetricCompressedDto.mapToNfbEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testNfbMetricDto() {
        NfbMetricDto dto = new NfbMetricDto(
                "individualNumber",
                "123",
                1L,
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        var result = NfbMetricDto.mapToNfbEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalBaselineDto() {
        PhysiologicalBaselineDto dto = new PhysiologicalBaselineDto(
                1L,
                "individualNumber",
                1L,
                "123",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        var result = dto.toEntity();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalMetricCompressedDto() {
        PhysiologicalMetricCompressedDto dto = new PhysiologicalMetricCompressedDto(
                "individualNumber",
                1L,
                "123",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6,
                1,
                0
        );

        var result = PhysiologicalMetricCompressedDto.mapToPhysiologicalEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalMetricDto() {
        PhysiologicalMetricDto dto = new PhysiologicalMetricDto(
                "individualNumber",
                1L,
                "123",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6,
                1,
                0
        );

        var result = PhysiologicalMetricDto.mapToPhysiologicalEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityBaselineDto() {
        ProductivityBaselineDto dto = new ProductivityBaselineDto(
                "123",
                "individualNumber",
                1L,
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6
        );

        var result = dto.toEntity();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityIndexDto() {
        ProductivityIndexDto dto = new ProductivityIndexDto(
                "123",
                "individualNumber",
                1L,
                1,
                "relaxation",
                "stress",
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6,
                true
        );

        var result = dto.toEntity();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityMetricCompressedDto() {
        ProductivityMetricCompressedDto dto = new ProductivityMetricCompressedDto(
                "individualNumber",
                1L,
                "123",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6
        );

        var result = ProductivityMetricCompressedDto.mapToProductivityEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityMetricDto() {
        ProductivityMetricDto dto = new ProductivityMetricDto(
                "individualNumber",
                1L,
                "123",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6
        );

        var result = ProductivityMetricDto.mapToProductivityEntity(dto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "expeditionId")
                .isEqualTo(dto);
    }
}
