package code.uica.code;

import java.util.Map;
import java.util.Set;

public interface UserService4UICA {
    boolean isUserRoot();
    Map<String, Set<Privilege4UICA>> getUserPrivileges();
}
