package com.arctic.backend_for_arctic_team.expedition;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import com.arctic.backend_for_arctic_team.expedition.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParticipantServiceTests {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ExpeditionRepository expeditionRepository;

    @Mock
    private UserRepository userRepository;

    private User leader;
    private Expedition expedition;
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDate startDate = LocalDate.now().plusMonths(1);
    private final LocalDate endDate = LocalDate.now().plusMonths(2);

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void testGetExpeditionParticipantsShouldThrowExpeditionNotFoundException(){
        // arrange
        Long expeditionId = 1L;

        when(expeditionRepository.existsById(expeditionId)).thenReturn(false);

        // act + assert
        Exception exception = assertThrows(ExpeditionNotFoundException.class, () -> participantService.getExpeditionParticipants(expeditionId));

    }


    @Nested
    class MappingResponsesTests{
        @Nested
        @DisplayName("ExpeditionResponse")
        class ExpeditionResponseTest {
            @BeforeEach
            void setUp() {
                 leader = User.builder()
                        .id(1L)
                        .lastName("Иванов")
                        .firstName("Иван")
                        .email("ivanov@example.com")
                        .individualNumber("ARCTIC")
                        .build();

                expedition = Expedition.builder()
                        .id(1L)
                        .name("name")
                        .description("desc")
                        .startDate(startDate)
                        .endDate(endDate)
                        .createdAt(now)
                        .leader(leader)
                        .build();

            }

            @Nested
            @DisplayName("mapFromEntityToResponse()")
            class MapFromEntityToResponse {

                @Test
                void shouldMapAllFieldsWithNullRole() {
                    // arrange
                    ExpeditionResponse expeditionResponse = new ExpeditionResponse(
                            1L,
                            "name",
                            startDate,
                            endDate,
                            null,
                            now,
                            "Иванов",
                            "Иван",
                            "ivanov@example.com",
                            "desc"
                    );

                    // act
                    ExpeditionResponse result = ExpeditionResponse.mapFromEntityToResponse(expedition);

                    // assert
                    assertThat(expeditionResponse)
                            .isNotNull()
                            .usingRecursiveComparison()
                            .isEqualTo(result);
                }

            }

            @Nested
            @DisplayName("forLeader()")
            class ForLeader {

                @Test
                @DisplayName("Маппинг полей для экспедиций, где пользователь лидер")
                void shouldMapAllFieldsWithLeaderRole() {
                    // arrange
                    ExpeditionResponse expeditionResponse = new ExpeditionResponse(
                            1L,
                            "name",
                            startDate,
                            endDate,
                            "LEADER",
                            now,
                            "Иванов",
                            "Иван",
                            "ivanov@example.com",
                            "desc"
                    );

                    // act
                    ExpeditionResponse result = ExpeditionResponse.forLeader(expedition);

                    // assert
                    assertThat(result)
                            .isNotNull()
                            .usingRecursiveComparison()
                            .isEqualTo(expeditionResponse);
                }
            }

            @Nested
            @DisplayName("forParticipant()")
            class ForParticipant {

                @Test
                @DisplayName("Маппинг полей для экспедиций, где пользователь просто участник")
                void shouldMapAllFieldsWithParticipantRole() {
                    // arrange
                    ExpeditionResponse expeditionResponse = new ExpeditionResponse(
                            1L,
                            "name",
                            startDate,
                            endDate,
                            "PARTICIPANT",
                            now,
                            "Иванов",
                            "Иван",
                            "ivanov@example.com",
                            "desc"
                    );

                    // act
                    ExpeditionResponse result = ExpeditionResponse.forParticipant(expedition);

                    // assert
                    assertThat(result)
                            .isNotNull()
                            .usingRecursiveComparison()
                            .isEqualTo(expeditionResponse);
                }

            }
        
            @Nested
            @DisplayName("Совместная проверка")
            class CommonBehavior {

                @Test
                @DisplayName("Проверка совпадения полей для всех методов за исключением поля role")
                void shouldPreserveAllExpeditionData() {
                    // act
                    ExpeditionResponse response1 = ExpeditionResponse.mapFromEntityToResponse(expedition);
                    ExpeditionResponse response2 = ExpeditionResponse.forLeader(expedition);
                    ExpeditionResponse response3 = ExpeditionResponse.forParticipant(expedition);

                    // assert
                    assertThat(response1)
                            .usingRecursiveComparison()
                            .ignoringFields("role")
                            .isEqualTo(response2)
                            .isEqualTo(response3);
                }

                @Test
                @DisplayName("Проверка правильности поля role для разных методов класса")
                void shouldHaveDifferentRoles() {
                    // act
                    ExpeditionResponse baseResponse = ExpeditionResponse.mapFromEntityToResponse(expedition);
                    ExpeditionResponse leaderResponse = ExpeditionResponse.forLeader(expedition);
                    ExpeditionResponse participantResponse = ExpeditionResponse.forParticipant(expedition);

                    // assert
                    assertThat(baseResponse.role()).isNull();
                    assertThat(leaderResponse.role()).isEqualTo("LEADER");
                    assertThat(participantResponse.role()).isEqualTo("PARTICIPANT");
                }
            }
            @Test
            @DisplayName("Проверка маппинга user в userResponse")
            void mapFromEntityToResponse(){
                // arrange
                UserResponse trueResponse = new UserResponse(
                        1L,
                        "ivanov@example.com",
                        "Иван",
                        "Иванов",
                        "ARCTIC"
                );

                // act
                UserResponse resultOfMethod = UserResponse.mapFromEntityToResponse(leader);

                // assert
                assertThat(resultOfMethod)
                        .usingRecursiveComparison()
                        .isEqualTo(trueResponse);
            }

            @Test
            @DisplayName("Проверка маппинга participant в participantResponse")
            void mapFromParticipantEntityToParticipantResponse(){
                // arrange
                UserResponse userResponse = new UserResponse(
                        1L,
                        "ivanov@example.com",
                        "Иван",
                        "Иванов",
                        "ARCTIC"
                );
                ParticipantResponse trueResponse = new ParticipantResponse(
                        1L,
                        userResponse,
                        now,
                        1L
                );
                Participant participant = Participant.builder()
                        .id(1L)
                        .user(leader)
                        .joinedAt(now)
                        .expedition(expedition)
                        .build();

                // act
                ParticipantResponse resultOfMethod = ParticipantResponse.mapFromEntityToResponse(participant);

                // assert
                assertThat(resultOfMethod)
                        .usingRecursiveComparison()
                        .isEqualTo(trueResponse);

            }
        }


    }
}
