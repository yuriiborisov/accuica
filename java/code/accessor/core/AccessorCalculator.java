package code.accessor.core;


import code.accessor.core.exception.AccessDeniedException;
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
@Slf4j
public class AccessorCalculator {

	private final AccessorServiceConfiguration configuration;

	public AccessorCalculator(AccessorServiceConfiguration configuration) {
		this.configuration = configuration;
	}


	/**
	 * Проверяет уровни доступа для заданной сущности заданный уровень
	 * Если доступа нет кидает code.accessor.core.exception. Иначе тихо выходит
	 * Если в привелегиях указан доступ full, то считается, что доступ есть и дальше не проверяет
	 * @param entityType Для чего проверяем доступ
	 * @param privilege какой доступ проверяем
	 */
	public void checkAccessForCurrentUser(String entityType, Privilege4Access privilege) throws AccessDeniedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(configuration.getUserService4Access().isGod(authentication)){
			return;
		}
		var hasAccess = authentication.getAuthorities().stream().anyMatch(
				authority-> ((GrantedAuthority4Access) authority).getRole().getGrantAccesses().stream()
						.anyMatch(access -> {
							Privilege4Access accessTypePrivilege =  configuration.getPrivilegeService4Access().getById(privilege.getId());
							return access.getEntityType().equals(entityType) && (configuration.getGrantAccessService4Access().hasPrivilege(access, configuration.getPrivilegeService4Access().getGodPrivilege())
									|| configuration.getGrantAccessService4Access().hasPrivilege(access, accessTypePrivilege));
						}));
		if(!hasAccess){
			log.info("Access Denied! user: {}, code.entity: {}, request access {} ", authentication.getDetails(), entityType, privilege );
			throw new AccessDeniedException(" EntityType: "+ entityType + ", requested access: " + privilege);
		}
	}

	/**
	 * Для данной сущности по данной роли вычисляет есть ли доступ
	 * @param
	 * @return
	 */
	public boolean calculateAccessGranted(Object entity, User4Access user, Privilege4Access privilege){
		return configuration.getJsEvaluator().execute(entity, user, privilege.getStatementScript());
	}

	public void checkAccessForCurrentUser(String entityType, List<Privilege4Access> privileges) throws AccessDeniedException {
		checkAccessForCurrentUser(entityType, null, privileges) ;
	}


	/**
	 * Получить для пользователя все привилегии для всех сущностей.
	 * @return
	 */
	public  Map<String, Set<Privilege4Access>> getCurrentUserPrivileges(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getUserPivils(authentication);
	}


	/**
	 * Собрать все привилегии для данного пользователя.
	 * @param user
	 * @return
	 */
	public  Map<String, Set<Privilege4Access>> getUserPivils(@NonNull Authentication user){
		var userRoles  = user.getAuthorities();
		Map<String, Set<Privilege4Access>> userPrivils = new HashMap<>();
		//Вытащить уникальные права пользователя из ролей и собрать их по entry
		userRoles.stream().forEach(
				grantedAuthority -> {
					if(grantedAuthority.getAuthority() != null) {
						Role4Access role4Access = configuration.getRoleService4Access().getById(grantedAuthority.getAuthority());
						if(role4Access != null){
							role4Access.getGrantAccesses().forEach(ga -> {
								String ndx = ga.getEntityType();
								userPrivils.computeIfAbsent(ndx, k -> new HashSet<>());
								userPrivils.get(ga.getEntityType()).addAll(ga.getPrivileges().stream().collect(Collectors.toSet()));
								getPrivileges(role4Access.getParent() == null ? null : role4Access.getParent().getId(), userPrivils);
							});
						}
					}
				});
		return userPrivils;
	}

	/**
	 * Для данной сущности по данной роли вычисляет есть ли доступ
	 * @param entityType Вид(класс) объекта, для которого проверяем доступ. Если доступ требует экземпляра, то он должен быть предоставлен в entity , иначе этот метод выйдет с исключением AccessDeniedException
	 * @param entity экземплар объекта, для которого проверяем доступы. Должен реализовывать интефейс ServiceRoleObject, и реализовывать методы проверки
	 * @param checkedPrivileges Запрашиваемые права доступа
	 * @throws AccessDeniedException вызывается если доступ запрещен
	 */
	public void checkAccessForCurrentUser(String entityType, Object entity, List<Privilege4Access> checkedPrivileges) throws AccessDeniedException {
		//Администратор может всё.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(configuration.getUserService4Access().isGod(authentication)){
			return ;
		}
		User4Access user = configuration.getUserService4Access().getUser(authentication);
		var userEntryPrivils = getCurrentUserPrivileges().get(entityType);
		//Вытащить уникальные права пользльзователя на запрашиваемую сущность?
			if(userEntryPrivils == null || userEntryPrivils.size() < 1){
		throw new AccessDeniedException("Access Denied!  Entry: " + entityType);
	}
        if(configuration.getPrivilegeService4Access().getGodPrivilege() != null && userEntryPrivils.contains(configuration.getPrivilegeService4Access().getGodPrivilege())){ // Полный доступ. Остальное не нужно
		return;
	}
	// В первую очередь проверяем обязательные привилегии - их приоритет меньше нуля
	List<Privilege4Access> requiredPrivls = checkedPrivileges.stream().filter(p -> p.getPriorityOrder() < 0).collect(Collectors.toList());
        for (Privilege4Access requiredPrivl : requiredPrivls) {
		if (userEntryPrivils.contains(requiredPrivl)) {
			checkPrivilegeApplicable(entityType, requiredPrivl);
			if (requiredPrivl.isCalculated()) {
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
	List<Privilege4Access> common = checkedPrivileges.stream().filter(p -> p.getPriorityOrder() >= 0)
			.filter(userEntryPrivils::contains).collect(Collectors.toList());
	List<Privilege4Access> sortedCheckedPrivils = common.stream().sorted(Comparator.comparing(Privilege4Access::getPriorityOrder)).collect(Collectors.toList());
        Collections.reverse(sortedCheckedPrivils);
	//пробежать по привилегиям в порядке применения.
        for(Privilege4Access checkPrivl : sortedCheckedPrivils){
			checkPrivilegeApplicable(entityType, checkPrivl);
		//Вычисляемые требуют объект, над которым будут вести действие.
		//Если нет - выкидывать сразу. Нельзя проверять подобные места такими ролями.
		if(checkPrivl.isCalculated()){
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
        throw new AccessDeniedException(" Access Denied!  Required access: "+ checkedPrivileges.stream().distinct().map(Privilege4Access::getId).collect(Collectors.joining(" || ")));
}

	private void checkPrivilegeApplicable(String entityType, Privilege4Access privilege4Access){
		if(privilege4Access.getApplicable() != null){
			if(!privilege4Access.getApplicable().getEntities().contains(entityType)){
				throw new AccessDeniedException(" Access Denied!  Privilege: {} is not applicable for Entity {}!", privilege4Access.getId(), entityType);
			}
		}
	}
	public  Map<String, Set<Privilege4Access>> getUserPrivileges(){
		Collection<? extends GrantedAuthority> userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Map<String, Set<Privilege4Access>> userPrivils = new HashMap<>();
		userRoles.stream().forEach(
				role-> {
					getPrivileges(role.getAuthority(), userPrivils);
				});
		return userPrivils;
	}
	private void getPrivileges(String id, Map<String, Set<Privilege4Access>> map){
		if(id != null) {
			Role4Access role = configuration.getRoleService4Access().getById(id);
			if(role != null){
				role.getGrantAccesses().forEach(ga -> {
					String entityType = ga.getEntityType();
					map.computeIfAbsent(entityType, k -> new HashSet<>());
					map.get(ga.getEntityType()).addAll(ga.getPrivileges().stream().collect(Collectors.toSet()));
					getPrivileges(role.getParent() == null ? null : role.getParent().getId(), map);
				});
			}
		}
	}
}
