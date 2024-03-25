package code.accessor.core.code;


import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.exception.AccessDeniedException;
import code.accessor.core.code.service.PrivilegeService4Access;
import code.accessor.core.code.service.RoleService4Access;
import code.accessor.core.code.service.UserService4Access;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Использует сервисы из основной части
 */
@Slf4j
@Component
public class AccessorCalculatorDbBased extends AccessorCalculatorAbstract{
	protected final UserService4Access userService4Access;
	protected final PrivilegeService4Access privilegeService4Access;
	public AccessorCalculatorDbBased(RoleService4Access roleService4Access, JsEvaluator4Access jsEvaluator4Access, UserService4Access userService4Access, PrivilegeService4Access privilegeService4Access) {
		super(roleService4Access, jsEvaluator4Access);
		this.userService4Access = userService4Access;
		this.privilegeService4Access = privilegeService4Access;
	}

	/**
	 * Для данной сущности по данной роли вычисляет есть ли доступ
	 * @param entityType Вид(класс) объекта, для которого проверяем доступ. Если доступ требует экземпляра, то он должен быть предоставлен в entity , иначе этот метод выйдет с исключением AccessDeniedException
	 * @param entity экземплар объекта, для которого проверяем доступы. Должен реализовывать интефейс ServiceRoleObject, и реализовывать методы проверки
	 * @param privilegesToCheck Запрашиваемые права доступа
	 * @throws AccessDeniedException вызывается если доступ запрещен
	 */
	public void checkAccessForCurrentUser(String entityType, Object entity, List<Privilege4Access> privilegesToCheck) throws AccessDeniedException {
		//Администратор может всё.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(userService4Access.isGod(authentication)){
			return ;
		}
		User4Access user = userService4Access.getUser(authentication);
		var userEntryPrivils = getCurrentUserPrivileges().get(entityType);
		//Вытащить уникальные права пользльзователя на запрашиваемую сущность?
			if(userEntryPrivils == null || userEntryPrivils.size() < 1){
		throw new AccessDeniedException("Access Denied!  Entry: " + entityType);
	}
        if(privilegeService4Access.getGodPrivilege() != null && userEntryPrivils.contains(privilegeService4Access.getGodPrivilege())){ // Полный доступ. Остальное не нужно
		return;
	}
	// В первую очередь проверяем обязательные привилегии - их приоритет меньше нуля
	List<Privilege4Access> requiredPrivls = privilegesToCheck.stream().filter(p -> p.getPriorityOrder() < 0).collect(Collectors.toList());
        for (Privilege4Access requiredPrivl : requiredPrivls) {
		if (userEntryPrivils.contains(requiredPrivl)) {
			checkPrivilegeApplicable(entityType, requiredPrivl);
			if (requiredPrivl.getCalculated()) {
				if (entity == null) {
					log.info("Access Denied! Entity is null but required! User:{}, Requested Access:{} ", user.getFullName(), requiredPrivl.getId());
					throw new AccessDeniedException("Access Denied! Entity is null but required! Requested access: " + requiredPrivl.getId());
				}
				if (!calculateAccessGranted(entity, user, requiredPrivl)) {
					log.info("Access Denied! User:{}, Requested Access:{}, Entity Id:{} ", user.getFullName(), requiredPrivl.getId(), entity);
					throw new AccessDeniedException("Access Denied! Requested access: " + requiredPrivl.getId());
				}
			}
		} else {
			log.info("Access Denied! User:{}, Requested Access:{}, Entity Id:{} ", user.getFullName(), requiredPrivl.getId(), entity);
			throw new AccessDeniedException("Access Denied! Requested access: " + requiredPrivl.getId());
		}
	}
	// Во вторую очередь проверяем необязательные привилегии (их приоритет больше или равен 0), из которых должна сработать только одна
	List<Privilege4Access> common = privilegesToCheck.stream().filter(p -> p.getPriorityOrder() >= 0)
			.filter(userEntryPrivils::contains).collect(Collectors.toList());
	List<Privilege4Access> sortedCheckedPrivils = common.stream().sorted(Comparator.comparing(Privilege4Access::getPriorityOrder)).collect(Collectors.toList());
        Collections.reverse(sortedCheckedPrivils);
	//пробежать по привилегиям в порядке применения.
        for(Privilege4Access checkPrivl : sortedCheckedPrivils){
			checkPrivilegeApplicable(entityType, checkPrivl);
		//Вычисляемые требуют объект, над которым будут вести действие.
		//Если нет - выкидывать сразу. Нельзя проверять подобные места такими ролями.
		if(checkPrivl.getCalculated()){
			if(entity == null){
				log.info("Access Denied! Entity is null but required! User:{}, Requested Access:{} ",user.getFullName(), checkPrivl.getId() );
				throw new AccessDeniedException(" Access Denied! Entity is null but required! Requested access: "+checkPrivl.getId());
			}
//                if(!calculateAccessGranted(code.entity, user, checkPrivl)){
//                    log.info("Access Denied! User:{}, Requested Access:{}, Entity Id:{} ",user.getFullName(), checkPrivl.getId(), code.entity);
//                    throw new AccessDeniedException(" Access Denied!  Requested access: "+checkPrivl.getId());
//                }
			// Проверяем все пересекающиеся привилегии до первого результата true
			if (calculateAccessGranted(entity, user, checkPrivl)) {
				return;
			}
		}
		//Если у пользователя есть запрошенная привилегия , то ок. Иначе идем далее
		else if(userEntryPrivils.contains(checkPrivl)){
			return; //Привилегия найдена. Вычисление если и было не вызвало эксепшн,
			// Т.к. привилегии отсортированы, то проверять далее нет смысла
		}
	}
        throw new AccessDeniedException(" Access Denied!  Required access: "+ privilegesToCheck.stream().distinct().map(Privilege4Access::getId).collect(Collectors.joining(" || ")));
	}
}
