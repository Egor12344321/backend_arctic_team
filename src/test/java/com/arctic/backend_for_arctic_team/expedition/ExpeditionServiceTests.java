package com.arctic.backend_for_arctic_team.expedition;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.exceptions.EditExpeditionException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.EditExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import com.arctic.backend_for_arctic_team.expedition.service.MapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpeditionServiceTests {

    @Mock private ExpeditionRepository expeditionRepository;

    @Mock private ParticipantRepository participantRepository;

    @Mock private MapperService mapperService;

    @Captor
    private ArgumentCaptor<Expedition> expeditionCaptor;

    private Expedition expedition;
    private Long expId;

    private Expedition savedExpedition;
    private User user;

    @InjectMocks ExpeditionService expeditionService;

    @BeforeEach
    public void setUp(){
         expedition = Expedition.builder()
                .name("name")
                .description("desc")
                .startDate(LocalDate.of(2025, 12,12))
                .endDate(LocalDate.of(2025, 12, 14))
                .build();
        user = User.builder()
                .email("user@mail.ru")
                .individualNumber("11111")
                .password("11111")
                .id(1L)
                .firstName("name")
                .lastName("lastName")
                .createdAt(LocalDateTime.of(2025, 1, 1, 1, 1, 1))
                .roles(Collections.emptySet())
                .build();
        savedExpedition = new Expedition(1L,
                "name",
                "desc",
                LocalDate.of(2025, 12,12),
                LocalDate.of(2025, 12, 14),
                Collections.emptyList(),
                user,
                LocalDateTime.of(2025, 12, 12, 12, 12, 12));
    }


    @Test void testCreateExpedition(){
        //arrange
        CreateExpeditionRequest createExpeditionRequest = new CreateExpeditionRequest("name", "desc", LocalDate.of(2025, 12,12), LocalDate.of(2025, 12, 14));


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


    @Nested
    class EditExpeditionTests {

        @BeforeEach
        public void setUp(){
            expId = 1L;
        }

        @Test
        void shouldReturnFullEditedExpedition() {
            //arrange
            EditExpeditionRequest expeditionRequest = new EditExpeditionRequest(
                    "otherName",
                    "otherDesc",
                    LocalDate.of(2025, 12, 14),
                    LocalDate.of(2025, 12, 16));

            Expedition editExpedition = Expedition.builder()
                    .name("otherName")
                    .description("otherDesc")
                    .startDate(LocalDate.of(2025, 12, 14))
                    .endDate(LocalDate.of(2025, 12, 16))
                    .participants(Collections.emptyList())
                    .createdAt(LocalDateTime.of(2025, 12, 12, 12, 12, 12))
                    .build();
            Long expId = 1L;
            when(expeditionRepository.findById(expId)).thenReturn(Optional.of(savedExpedition));
            when(expeditionRepository.save(expeditionCaptor.capture())).thenReturn(editExpedition);

            //act
            expeditionService.editExpedition(expId, expeditionRequest);

            //assert
            verify(expeditionRepository).save(expeditionCaptor.capture());

            Expedition arg = expeditionCaptor.getValue();
            assertEquals("otherName", arg.getName());
            assertEquals("otherDesc", arg.getDescription());
            assertEquals(LocalDate.of(2025, 12, 14), arg.getStartDate());
            assertEquals(LocalDate.of(2025, 12, 16), arg.getEndDate());

        }

        @Test
        @DisplayName("Проверка выброса исключения при редактировании конца экспедиции, который меньше существующего начала")
        void shouldThrowEditExpeditionExceptionWhenEndDateLowerThanStartDateOfSavedExpedition(){
            //arrange
            EditExpeditionRequest expeditionRequest = new EditExpeditionRequest(
                    null,
                    null,
                    null,
                    LocalDate.of(2025, 12, 1)); // В сохраненной экспедиции день = 14

            when(expeditionRepository.findById(expId)).thenReturn(Optional.of(savedExpedition));

            //act + assert
            EditExpeditionException editExpeditionException = assertThrows(EditExpeditionException.class, () -> expeditionService.editExpedition(expId, expeditionRequest));

            assertEquals("Дата начала экспедиции должна быть позже даты окончания", editExpeditionException.getMessage());
        }


    }
}
