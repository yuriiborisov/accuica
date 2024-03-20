package code.accessor.core.code.service;

import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService4Access {
    List<Role4AccessResponse> getUserRolesFromAuthentication(Authentication auth);
    boolean isGod(Authentication auth);

    User4Access getUser(Authentication auth);

}
