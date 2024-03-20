package code.accessor.core;

import java.util.List;

public interface MethodAccess4Access {

    String getEntityType();
    String getKey();
    String getHumanName();

    String getDescription();
    String getMethod();
    List<Privilege4Access> getPrivileges();
}
