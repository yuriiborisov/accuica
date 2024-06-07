package code.accessor.core.code;


import code.accessor.core.GrantedAuthority4Access;
import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.exception.AccessDeniedException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Использует сервисы из основной части
 */
public interface AccessorCalculator {

	/**
	 * Для данной сущности по данной роли вычисляет есть ли доступ
	 * @param entityType Вид(класс) объекта, для которого проверяем доступ. Если доступ требует экземпляра, то он должен быть предоставлен в entity , иначе этот метод выйдет с исключением AccessDeniedException
	 * @param entity экземплар объекта, для которого проверяем доступы. Должен реализовывать интефейс ServiceRoleObject, и реализовывать методы проверки
	 * @param checkedPrivileges Запрашиваемые права доступа
	 * @throws AccessDeniedException вызывается если доступ запрещен
	 */
	void checkAccessForCurrentUser(String entityType, Object entity, List<Privilege4Access> checkedPrivileges) throws AccessDeniedException;


	Map<String, Set<Privilege4Access>> getUserPrivileges();
}
