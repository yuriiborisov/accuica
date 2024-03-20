package code.accessor.core;

public interface PrivilegeService4Access {
//    Privilege4Access map(Object privilege);
    Privilege4Access getById(String id);
    Privilege4Access getGodPrivilege();
}
