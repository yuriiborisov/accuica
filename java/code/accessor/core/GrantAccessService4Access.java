package code.accessor.core;

import java.util.stream.Collectors;

public interface GrantAccessService4Access {
//    GrantAccess4Access map(Object grantAccessEntity);
//    GrantAccess4Access getById(String id);

    default boolean hasPrivilege(GrantAccess4Access grantAccess4Access, final Privilege4Access privilegeId) {
        return grantAccess4Access.getPrivileges().stream().collect(Collectors.toList()).contains(privilegeId);
    }
}
