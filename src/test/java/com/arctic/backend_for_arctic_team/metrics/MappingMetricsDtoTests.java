package com.arctic.backend_for_arctic_team.metrics;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MappingMetricsDtoTests {
    @Test
    void testCardioMetricCompressedDto() {
        // arrange
        CardioMetricCompressedDto dto = new CardioMetricCompressedDto(
                "individualNumber",
                1L,
                "expeditionId",
                1,
                60.0,
                1,
                0.5,
                1,
                0,
                1,
                0.3
        );

        // act
        var result = CardioMetricCompressedDto.mapToCardioEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testCardioMetricDto() {
        // arrange
        CardioMetricDto dto = new CardioMetricDto(
                "individualNumber",
                1L,
                "expeditionId",
                1,
                60.0,
                1,
                0.5,
                1,
                0,
                1,
                0.3
        );

        // act
        var result = CardioMetricDto.mapToCardioEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGArtifactsMetricCompressedDto() {
        // arrange
        EEGArtifactsMetricCompressedDto dto = new EEGArtifactsMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                1,
                0,
                1,
                0
        );

        // act
        var result = EEGArtifactsMetricCompressedDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGArtifactsMetricDto() {
        // arrange
        EEGArtifactsMetricDto dto = new EEGArtifactsMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                1,
                0,
                1,
                0
        );

        // act
        var result = EEGArtifactsMetricDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGProceedMetricCompressedDto() {
        // arrange
        EEGProceedMetricCompressedDto dto = new EEGProceedMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5f,
                0.6f
        );

        // act
        var result = EEGProceedMetricCompressedDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGProceedMetricDto() {
        // arrange
        EEGProceedMetricDto dto = new EEGProceedMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5f,
                0.6f
        );

        // act
        var result = EEGProceedMetricDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGRawMetricCompressedDto() {
        // arrange
        EEGRawMetricCompressedDto dto = new EEGRawMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5f,
                0.6f
        );

        // act
        var result = EEGRawMetricCompressedDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEEGRawMetricDto() {
        // arrange
        EEGRawMetricDto dto = new EEGRawMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5f,
                0.6f
        );

        // act
        var result = EEGRawMetricDto.mapFromRequestToEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEmotionalMetricCompressedDto() {
        // arrange
        EmotionalMetricCompressedDto dto = new EmotionalMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5,
                0.6,
                0.7,
                0.8,
                0.9
        );

        // act
        var result = EmotionalMetricCompressedDto.mapToEmotionalEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testEmotionalMetricDto() {
        // arrange
        EmotionalMetricDto dto = new EmotionalMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.5,
                0.6,
                0.7,
                0.8,
                0.9
        );

        // act
        var result = EmotionalMetricDto.mapToEmotionalEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testMemsMetricCompressedDto() {
        // arrange
        MemsMetricCompressedDto dto = new MemsMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0
        );

        // act
        var result = MemsMetricCompressedDto.mapToMemsEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testMemsMetricDto() {
        // arrange
        MemsMetricDto dto = new MemsMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0
        );

        // act
        var result = MemsMetricDto.mapToMemsEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testNfbMetricCompressedDto() {
        // arrange
        NfbMetricCompressedDto dto = new NfbMetricCompressedDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        // act
        var result = NfbMetricCompressedDto.mapToNfbEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testNfbMetricDto() {
        // arrange
        NfbMetricDto dto = new NfbMetricDto(
                "individualNumber",
                "expeditionId",
                1L,
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        // act
        var result = NfbMetricDto.mapToNfbEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalBaselineDto() {
        // arrange
        PhysiologicalBaselineDto dto = new PhysiologicalBaselineDto(
                1L,
                "individualNumber",
                1L,
                "expeditionId",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5
        );

        // act
        var result = dto.toEntity();

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalMetricCompressedDto() {
        // arrange
        PhysiologicalMetricCompressedDto dto = new PhysiologicalMetricCompressedDto(
                "individualNumber",
                1L,
                "expeditionId",
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

        // act
        var result = PhysiologicalMetricCompressedDto.mapToPhysiologicalEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testPhysiologicalMetricDto() {
        // arrange
        PhysiologicalMetricDto dto = new PhysiologicalMetricDto(
                "individualNumber",
                1L,
                "expeditionId",
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

        // act
        var result = PhysiologicalMetricDto.mapToPhysiologicalEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityBaselineDto() {
        // arrange
        ProductivityBaselineDto dto = new ProductivityBaselineDto(
                "expeditionId",
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

        // act
        var result = dto.toEntity();

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityIndexDto() {
        // arrange
        ProductivityIndexDto dto = new ProductivityIndexDto(
                "expeditionId",
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

        // act
        var result = dto.toEntity();

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityMetricCompressedDto() {
        // arrange
        ProductivityMetricCompressedDto dto = new ProductivityMetricCompressedDto(
                "individualNumber",
                1L,
                "expeditionId",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6
        );

        // act
        var result = ProductivityMetricCompressedDto.mapToProductivityEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void testProductivityMetricDto() {
        // arrange
        ProductivityMetricDto dto = new ProductivityMetricDto(
                "individualNumber",
                1L,
                "expeditionId",
                1,
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6
        );

        // act
        var result = ProductivityMetricDto.mapToProductivityEntity(dto);

        // assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }
}
