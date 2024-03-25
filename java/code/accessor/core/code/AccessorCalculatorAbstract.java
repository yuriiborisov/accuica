package code.accessor.core.code;


import code.accessor.core.code.dto.User4Access;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.exception.AccessDeniedException;
import code.accessor.core.code.service.RoleService4Access;
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
public abstract class AccessorCalculatorAbstract implements AccessorCalculator{

	protected final RoleService4Access roleService4Access;
	protected final JsEvaluator4Access jsEvaluator4Access;

	public AccessorCalculatorAbstract(RoleService4Access roleService4Access, JsEvaluator4Access jsEvaluator4Access) {
		this.roleService4Access = roleService4Access;
		this.jsEvaluator4Access = jsEvaluator4Access;
	}

	public abstract void checkAccessForCurrentUser(String entityType, Object entity, List<Privilege4Access> checkedPrivileges) throws AccessDeniedException;

	/**
	 * Для данной сущности по данной роли вычисляет есть ли доступ
	 * @param
	 * @return
	 */
	public boolean calculateAccessGranted(Object entity, User4Access user, Privilege4Access privilege){
		return jsEvaluator4Access.execute(entity, user, privilege.getStatementScript());
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
						Role4AccessResponse role4AccessResponse = roleService4Access.getById(grantedAuthority.getAuthority());
						if(role4AccessResponse != null){
							role4AccessResponse.getGrantAccesses().forEach(ga -> {
								String ndx = ga.getEntityType();
								userPrivils.computeIfAbsent(ndx, k -> new HashSet<>());
								userPrivils.get(ga.getEntityType()).addAll(ga.getPrivileges().stream().collect(Collectors.toSet()));
								getPrivileges(role4AccessResponse.getParent() == null ? null : role4AccessResponse.getParent().getId(), userPrivils);
							});
						}
					}
				});
		return userPrivils;
	}

	protected void checkPrivilegeApplicable(String entityType, Privilege4Access privilege4Access){
		if(privilege4Access.getApplicable() != null){
			if(!privilege4Access.getApplicable().getEntities().contains(entityType)){
				throw new AccessDeniedException(" Access Denied!  Privilege: {} is not applicable for Entity {}!", privilege4Access.getId(), entityType);
			}
		}
	}
	public Map<String, Set<Privilege4Access>> getUserPrivileges(){
		Collection<? extends GrantedAuthority> userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Map<String, Set<Privilege4Access>> userPrivils = new HashMap<>();
		userRoles.stream().forEach(
				role-> {
					getPrivileges(role.getAuthority(), userPrivils);
				});
		return userPrivils;
	}
	protected void getPrivileges(String id, Map<String, Set<Privilege4Access>> map){
		if(id != null) {
			Role4AccessResponse role = roleService4Access.getById(id);
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
