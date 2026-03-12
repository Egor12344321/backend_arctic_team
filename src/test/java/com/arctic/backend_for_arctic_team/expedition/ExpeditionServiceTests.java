package com.arctic.backend_for_arctic_team.expedition;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import com.arctic.backend_for_arctic_team.expedition.service.MapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpeditionServiceTests {

    @Mock private ExpeditionRepository expeditionRepository;

    @Mock private ParticipantRepository participantRepository;

    @Mock private MapperService mapperService;

    @InjectMocks ExpeditionService expeditionService;

    @Test void testCreateExpedition(){
        //arrange
        CreateExpeditionRequest createExpeditionRequest = new CreateExpeditionRequest("name", "desc", LocalDate.of(2025, 12,12), LocalDate.of(2025, 12, 14));

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
                .build();
        Expedition savedExpedition = new Expedition(1L,
                "name",
                "desc",
                LocalDate.of(2025, 12,12),
                LocalDate.of(2025, 12, 14),
                Collections.emptyList(),
                user,
                LocalDateTime.of(2025, 12, 12, 12, 12, 12));


        ExpeditionResponse expeditionResponse = new ExpeditionResponse(1L, "name", LocalDate.of(2025, 12,12), LocalDate.of(2025, 12, 14), null, LocalDateTime.of(2025, 12, 12, 12, 12, 12), user.getLastName(), user.getFirstName(), user.getEmail(), "desc");

        when(mapperService.mapFromRequestToEntity(createExpeditionRequest, user)).thenReturn(expedition);
        when(expeditionRepository.save(expedition)).thenReturn(savedExpedition);

        //act
        ExpeditionResponse response = expeditionService.createExpedition(createExpeditionRequest, user);

        //assert
        assertThat(expeditionResponse)
                .usingRecursiveComparison()
                .isEqualTo(response);

        verify(mapperService).mapFromRequestToEntity(createExpeditionRequest, user);
        verify(expeditionRepository).save(expedition);
    }
}
