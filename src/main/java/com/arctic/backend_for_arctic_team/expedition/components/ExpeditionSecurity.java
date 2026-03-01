package com.arctic.backend_for_arctic_team.expedition.components;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("expeditionSecurity")
@Slf4j
@RequiredArgsConstructor
public class ExpeditionSecurity {
    private final ExpeditionService expeditionService;


    // проверка является ли пользователь реальным лидером экпедиции, а не просто пользователь с ролью экспедиции
    public boolean isLeaderOfExpedition(Authentication authentication, Long expeditionId) {
        log.info("AUTHENTICATION: {}, {}", authentication, authentication.getName());
        Long leaderId = getCurrentUser(authentication).getId();
        log.info("ПРОВЕРКААААА: {}", expeditionService.isLeaderOfExpedition(leaderId, expeditionId));
        return expeditionService.isLeaderOfExpedition(expeditionId, leaderId);
    }

    // проверка является ли пользователь участником экспедиции
    public boolean isParticipantOfExpedition(Authentication authentication, Long expeditionId){
        Long userId = getCurrentUser(authentication).getId();
        log.info("AUTHENTICATION: {}, {}", authentication, authentication.getName());
        log.info("ПРОВЕРКААААА: {}", expeditionService.isParticipantOfExpedition(userId, expeditionId));

        return expeditionService.isParticipantOfExpedition(expeditionId, userId);
    }

    private User getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }

        log.error("Principal is not User object");
        return null;
    }
}
