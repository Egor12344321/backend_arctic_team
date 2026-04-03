package com.arctic.backend_for_arctic_team.metrics.service.charts.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "charts.service")
@Component
@Getter
@Setter
public class ChartsServiceProperties {
    private String url;
    private final List<String> enabledCharts = List.of("alpha-beta-theta", "fatigue", "heart-rate", "psychological-fatigue", "gravity", "concentration", "relaxation");
}
