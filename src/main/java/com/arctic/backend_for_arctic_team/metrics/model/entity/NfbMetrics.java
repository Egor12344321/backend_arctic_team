package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nfb_metrics")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NfbMetrics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expedition_id")
    private Long expeditionId;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "session")
    private Integer session;

    @Column(name = "alpha")
    private Double alpha;

    @Column(name = "beta")
    private Double beta;

    @Column(name = "theta")
    private Double theta;

    @Column(name = "delta")
    private Double delta;

    @Column(name = "smr")
    private Double smr;

}
