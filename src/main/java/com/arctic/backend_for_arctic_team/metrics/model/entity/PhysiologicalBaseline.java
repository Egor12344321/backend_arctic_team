package com.arctic.backend_for_arctic_team.metrics.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "physiological_baseline")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PhysiologicalBaseline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "expedition_id")
    private Long expeditionId;

    @Column(name = "session")
    private Integer session;


    @Column(name = "alpha")
    private Double alpha;

    @Column(name = "beta")
    private Double beta;

    @Column(name = "alpha_gravity")
    private Double alphaGravity;

    @Column(name = "beta_gravity")
    private Double betaGravity;

    @Column(name = "concentration")
    private Double concentration;
}
