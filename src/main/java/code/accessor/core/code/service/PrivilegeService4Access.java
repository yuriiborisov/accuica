package code.accessor.core.code.service;

import code.accessor.core.code.Privilege4Access;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PrivilegeService4Access {
//    Privilege4Access map(Object privilege);
    Privilege4Access getById(String id);
    Privilege4Access getGodPrivilege();
    List<Privilege4Access> getAll();
    Privilege4Access create(Privilege4Access privilege);
    Privilege4Access update(Privilege4Access privilege);
    void delete(final String id);
}
