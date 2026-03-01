package com.arctic.backend_for_arctic_team.expedition.components;


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
        Long leaderId = getCurrentUserId(authentication);
        return expeditionService.isLeaderOfExpedition(leaderId, expeditionId);
    }

    // проверка является ли пользователь участником экспедиции
    public boolean isParticipantOfExpedition(Authentication authentication, Long expeditionId){
        Long userId = getCurrentUserId(authentication);
        return expeditionService.isParticipantOfExpedition(userId, expeditionId);
    }
    private Long getCurrentUserId(Authentication authentication) {
        return Long.valueOf(authentication.getName());
    }
}
