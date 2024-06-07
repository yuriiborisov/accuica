package code.accessor.core.code.dto;

import code.accessor.core.code.Privilege4Access;

import java.util.List;

public interface GrantAccess4Access {
    String getEntityType();
    List<Privilege4Access> getPrivileges();
}
