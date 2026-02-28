package com.arctic.backend_for_arctic_team.metrics.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nfb_metrics_compressed")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NfbMetricsCompressed {
    @Id
    @Column(name = "id")
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
