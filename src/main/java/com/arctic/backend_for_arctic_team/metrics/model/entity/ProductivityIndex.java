package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productivity_index")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductivityIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expedition_id")
    private String expeditionId;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "relaxation")
    private String relaxation;

    @Column(name = "stress")
    private String stress;

    @Column(name = "gravity_baseline")
    private Double gravityBaseline;

    @Column(name = "productivity_baseline")
    private Double productivityBaseline;

    @Column(name = "fatigue_baseline")
    private Double fatigueBaseline;

    @Column(name = "reverse_fatigue_baseline")
    private Double reverseFatigueBaseline;

    @Column(name = "relaxation_baseline")
    private Double relaxationBaseline;

    @Column(name = "concentration_baseline")
    private Double concentrationBaseline;

    @Column(name = "has_artifacts")
    private Boolean hasArtifacts;

}
