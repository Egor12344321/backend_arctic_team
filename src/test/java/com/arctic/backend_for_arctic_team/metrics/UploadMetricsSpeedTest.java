//package com.arctic.backend_for_arctic_team.metrics;
//
//import com.arctic.backend_for_arctic_team.metrics.model.dto.request.CardioMetricDto;
//import com.arctic.backend_for_arctic_team.metrics.model.dto.request.NfbMetricDto;
//import com.arctic.backend_for_arctic_team.metrics.model.dto.request.UploadRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
//class UploadServicePerformanceTest {
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
//            .withDatabaseName("arctic_team")
//            .withUsername("postgres")
//            .withPassword("egor")
//            .withReuse(true);
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
//        registry.add("logging.level.com.arctic=ERROR");
//        registry.add("spring.jpa.show-sql", () -> "false");
//    }
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    void testLargeUpload_50KRecords() throws Exception {
//        System.out.println("\n🚀 Testing 50K EEG records (≈10MB)");
//
//        UploadRequest request = generateRealisticTestData(50_000);
//
//        StopWatch watch = new StopWatch("50K EEG Upload");
//        watch.start("JSON serialization");
//        String json = objectMapper.writeValueAsString(request);
//        watch.stop();
//
//        System.out.printf("📦 JSON size: %.2f MB%n", json.getBytes().length / 1024.0 / 1024.0);
//
//        watch.start("HTTP + DB insert");
//        mockMvc.perform(post("/api/upload")  // ← Твой endpoint!
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andDo(result -> {
//                    watch.stop();
//                    System.out.printf("✅ TOTAL: %.2f sec (%.0f ms)%n",
//                            watch.getTotalTimeSeconds(), watch.getTotalTimeMillis());
//                    System.out.printf("⚡ Speed: %.0f records/sec%n",
//                            50_000 / watch.getTotalTimeSeconds());
//                });
//    }
//
//    private UploadRequest generateRealisticTestData(int eegCount) {
//        // 🎯 Генерируем РЕАЛИСТИЧНЫЕ EEG данные (50K точек)
//        List<EEGRawMetricDto> eegRaw = IntStream.range(0, eegCount)
//                .mapToObj(i -> new EEGRawMetricDto(  // ← Твоя реальная DTO
//                        i + 1640995200000L,  // timestamp
//                        "session-" + (i / 1000),
//                        "exp-123",
//                        Timestamp.from(Instant.now()),
//                        (float) (Math.sin(i * 0.01) * 0.5 + (Math.random() - 0.5) * 0.1),  // channel1: синусоида + шум
//                        (float) (Math.cos(i * 0.01) * 0.3 + (Math.random() - 0.5) * 0.08),  // channel2
//                        i % 100 == 0  // isMarked каждые 100 точек
//                ))
//                .toList();
//
//        // Малые сэмплы остальных типов (по 100 записей)
//        List<CardioMetricDto> cardio = generateCardioSample(100);
//        List<NfbMetricDto> nfb = generateNfbSample(100);
//
//        return new UploadRequest(
//                cardio,           // cardioMetrics
//                List.of(),        // emotionalMetrics
//                List.of(),        // memsMetrics
//                nfb,              // nfbMetrics
//                List.of(),        // physiologicalMetrics
//                List.of(),        // productivityMetrics
//                List.of(),        // EEGArtifactsMetrics
//                List.of(),        // EEGProceedMetrics
//                eegRaw,           // EEGRawMetrics ← ГЛАВНЫЙ НАБОР!
//                List.of(),        // cardioMetricsCompressed
//                List.of(),        // emotionalMetricsCompressed
//                List.of(),        // memsMetricsCompressed
//                List.of(),        // nfbMetricsCompressed
//                List.of(),        // physiologicalMetricsCompressed
//                List.of(),        // productivityMetricsCompressed
//                List.of(),        // EEGArtifactsMetricsCompressed
//                List.of(),        // EEGProceedMetricsCompressed
//                List.of()         // EEGRawMetricsCompressed
//        );
//    }
//
//    // Генераторы для остальных DTO (по 100 записей)
//    private List<CardioMetricDto> generateCardioSample(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> new CardioMetricDto(
//                        i + 1640995200000L,
//                        "cardio-" + i,
//                        "exp-123",
//                        Timestamp.from(Instant.now()),
//                        60 + (float)(Math.random() * 40),  // heartRate 60-100
//                        false,
//                        (float)(Math.random() * 1.0),
//                        true,
//                        false,
//                        true,
//                        (float)(Math.random() * 0.5),
//                        false
//                )).toList();
//    }
//
//    private List<NfbMetricDto> generateNfbSample(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> new NfbMetricDto(
//                        i + 1640995200000L,
//                        "nfb-" + i,
//                        "exp-123",
//                        Timestamp.from(Instant.now()),
//                        (float)(0.05 + Math.random() * 0.2),  // alpha
//                        (float)(0.1 + Math.random() * 0.3),   // beta
//                        (float)(0.02 + Math.random() * 0.1),  // theta
//                        (float)(0.01 + Math.random() * 0.05), // delta
//                        (float)(0.08 + Math.random() * 0.15), // smr
//                        false
//                )).toList();
//    }
//}
