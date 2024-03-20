package code.accessor.core;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService4Access {
    List<Role4Access> getUserRolesFromAuthentication(Authentication auth);
    boolean isGod(Authentication auth);

    User4Access getUser(Authentication auth);

}
