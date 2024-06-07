package code.uica.code;

import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

public interface UserService4UICA {
    boolean isGod(Authentication auth);
    Map<String, Set<Privilege4UICA>> getUserPrivilegesByEntity(Authentication auth);
}
