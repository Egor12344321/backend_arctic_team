package com.arctic.backend_for_arctic_team.auth.service_interface;

import com.arctic.backend_for_arctic_team.auth.dto.response.user_responses.UserSearchResponse;

public interface UserService {
    public UserSearchResponse searchUserByIndividualNumber(String individualNumber);
}
