package com.arctic.backend_for_arctic_team.auth.service_interface;

import com.arctic.backend_for_arctic_team.auth.dto.reponse.user_responses.UserSearchResponse;

public interface UserService {
    public UserSearchResponse searchUserByIndividualNumber(String individualNumber);
}
