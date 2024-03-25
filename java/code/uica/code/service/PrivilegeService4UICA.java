package code.uica.code.service;

import code.uica.code.Privilege4UICA;

import java.util.List;

public interface PrivilegeService4UICA {
    Privilege4UICA getById(String id);

    List<Privilege4UICA> getAll();
    Privilege4UICA getGodPrivilege();
}
