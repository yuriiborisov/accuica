package code.accessor.core.code;

import code.accessor.core.code.exception.AccessDeniedException;

/**
 * Сигнальный интерфейс для акцесссоров
 */
public interface Accessor {

    void create() throws AccessDeniedException;

    void edit() throws AccessDeniedException;

    void read() throws AccessDeniedException;

    void delete() throws AccessDeniedException;
}
