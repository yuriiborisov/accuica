package code.accessor.core;

import code.accessor.core.exception.AccessDeniedException;

/**
 * Сигнальный интерфейс для акцесссоров
 */
public interface Accessor {

    void create() throws AccessDeniedException;

    void edit() throws AccessDeniedException;

    void read() throws AccessDeniedException;

    void delete() throws AccessDeniedException;
}
