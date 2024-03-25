package code.uica.code.service;

import code.uica.code.Holder4UICA;

import java.util.List;

public interface HolderService4UICA {
    Holder4UICA getById(String id);

    List<Holder4UICA> getAll();
    Holder4UICA create(Holder4UICA holder4UICA);
    Holder4UICA update(Holder4UICA holder4UICA);
}
