package com.arctic.backend_for_arctic_team.expedition;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.service.MapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class MapServiceTests {

    private final MapperService mapperService = new MapperService();

    @Test
    void testMapFromRequestToEntity(){
        //arrange
        User user = User.builder()
                .email("user@mail.ru")
                .individualNumber("11111")
                .password("11111")
                .id(1L)
                .firstName("name")
                .lastName("lastName")
                .createdAt(LocalDateTime.of(2025, 1, 1, 1, 1, 1))
                .roles(Collections.emptySet())
                .build();

        Expedition expedition = Expedition.builder()
                .name("name")
                .description("desc")
                .startDate(LocalDate.of(2025, 12,12))
                .endDate(LocalDate.of(2025, 12, 14))
                .leader(user)
                .build();

        CreateExpeditionRequest createExpeditionRequest = new CreateExpeditionRequest("name", "desc", LocalDate.of(2025, 12,12), LocalDate.of(2025, 12, 14));

        // act
        Expedition result = mapperService.mapFromRequestToEntity(createExpeditionRequest, user);

        // assert
        assertThat(expedition)
                .usingRecursiveComparison()
                .isEqualTo(result);

    }
}
