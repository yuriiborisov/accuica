package code.accessor;

import code.accessor.core.Role4Access;
import code.accessor.core.RoleService4Access;
import code.accessor.core.User4Access;
import code.accessor.core.UserService4Access;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService4AccessImpl implements UserService4Access {
    RoleService4Access roleService4Access;
    @Override
    public List<Role4Access> getUserRolesFromAuthentication(Authentication auth) {
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
