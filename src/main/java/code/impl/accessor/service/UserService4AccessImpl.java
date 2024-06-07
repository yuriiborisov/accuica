package code.impl.accessor.service;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.service.RoleService4Access;
import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.service.UserService4Access;
import code.uica.code.Privilege4UICA;
import code.uica.code.UserService4UICA;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService4AccessImpl implements UserService4Access, UserService4UICA {
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

    @Override
    public Map<String, Set<Privilege4UICA>> getUserPrivilegesByEntity(Authentication auth) {
        return roleService4Access.getById("ADMINISTRATOR").getGrantAccesses().stream()
                .collect(Collectors.toMap( k -> k.getEntityType(), v -> v.getPrivileges().stream().map(e ->(Privilege4UICA) e).collect(Collectors.toSet()), (e0,e1) -> e1));
    }
}
