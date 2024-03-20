package code.accessor.impl.service;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.service.RoleService4Access;
import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.service.UserService4Access;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService4AccessImpl implements UserService4Access {
    RoleService4Access roleService4Access;
    @Override
    public List<Role4AccessResponse> getUserRolesFromAuthentication(Authentication auth) {
        return List.of(roleService4Access.getById("ADMINISTRATOR"));
    }

    @Override
    public boolean isGod(Authentication auth) {
        return false;
    }

    @Override
    public User4Access getUser(Authentication auth) {
        return () -> "testUser";
    }
}
