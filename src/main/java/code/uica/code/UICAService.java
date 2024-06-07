package code.uica.code;

import code.uica.impl.dto.UICARequest;
import code.uica.impl.dto.EntityUICAResponse;
import code.uica.impl.dto.UICAItemResponse;
import code.uica.code.exception.UICAException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
//UICA - Uni-graphic Interface Component Access
public class UICAService {

//	private final UICARepository repository;
//	private final UICAMapper mapper;
//	private final UserService4UICA userService;
	private final UICAServiceConfiguration configuration;
	private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	public UICAService(final UICAServiceConfiguration configuration) {
		this.configuration = configuration;
	}

//	/**
//	 * Read UICAHolder
//	 *
//	 * @param formId
//	 * @return
//	 * @throws UICAException
//	 */
//	public UICAHolderDto getUICA(final String formId) throws UICAException {
//		UICAHolderDto holder = getById(formId);
//		if (holder != null) {
//			return holder;
//		}
//		throw new UICAException("UICA code.entity with id '" + formId + "' not found!");
//	}

	/**
	 * Get accesses for requested ui-components
	 *
	 * @param request
	 * @return
	 * @throws UICAException

	 */
	public EntityUICAResponse getPermissions(final UICARequest request, final Map<String, String> params) throws UICAException {
		Config4UICA config = getConfig(request.getFormId());
		return eval(params, config, request);
	}

//	/**
//	 * Create UICAHolder
//	 *
//	 * @param request
//	 * @return
//	 * @throws UICAException
//	 */
//	public Holder4UICA create(Holder4UICA request) throws UICAException {
//		final String formId = request.getFormId();
//		Holder4UICA holder4UICA = configuration.getHolderService4UICA().getById(request.getFormId());
//		if (holder4UICA == null) {
//			return holder4UICA;
//		}
//		throw new UICAException("UICA holder with id '" + formId + "' already exists!");
//	}
//
//	/**
//	 * Update UICAHolder
//	 *
//	 * @param request
//	 * @return
//	 * @throws UICAException
//	 */
//	public UICAHolderDto update(UICAHolderDto request) throws UICAException {
//		Holder4UICA holder4UICA = configuration.getHolderService4UICA().getById(request.getFormId());
//		if (holder4UICA != null) {
//			UICAHolderEntity entity = mapper.map(request);
//			return mapper.map(repository.save(entity));
//		}
//		throw new UICAException("UICA holder with id '" + formId + "' not found!");
//	}

	/**
	 * Calculate accesses to ui-components
	 *
	 * @param config  code.configuration from DB
	 * @param request request - formId and list of ui-comonents ids
	 * @return UICAResponse
	 */
	private EntityUICAResponse eval(final Map<String, String> params, final Config4UICA config, final UICARequest request)
			throws UICAException {

		// Создаем пустой ответ
		Map<String, UICAItemResponse> uicaMap = new HashMap<>();
		// Пустой список доступов к компонентам
		List<UICAItemResponse> componentsAccessResponse = new ArrayList<>();
		// У каждой сущности есть набор привилегий, получаем их список по типу сущности через роли текущего пользователя
		final Set<Privilege4UICA> userPrivileges = getUserPrivileges(config.getEntity());
		// Определяем через роли пользователя есть ли у него привилегия делать все
		boolean userAdminOrGod = isUserGod(userPrivileges);

		// Получаем мапу (ид компонента, структура доступов)
		Map<String, Config4UICA.UICAHolderItem> configMap = config.getComponentAccess() == null ?
				Collections.emptyMap() :
				config.getComponentAccess().stream().collect(Collectors.toMap(k -> k.getComponentId(), v -> v));
		// Получаем корневую сущность которая будет в ответе
		final Object entity = getEntity(params, config.getEntity());

		// Эта ветка редко используется
		if (request.getComponentIds() != null) {
			for (String id : request.getComponentIds()) {
				UICAItemResponse componentAccessResponse = new UICAItemResponse();
				Set<UICAState> allowed = new HashSet<>();
				componentAccessResponse.setAccess(UICAState.HIDDEN);
				componentAccessResponse.setStatus(UICAStatus.NOT_FOUND);

				if(userAdminOrGod){
					componentAccessResponse.setAccess(UICAState.EDIT);
					componentAccessResponse.setStatus(UICAStatus.OK);
				}else{
					if (configMap.containsKey(id)) {
						Config4UICA.UICAHolderItem holderItem = configMap.get(id);
						if (request.getCreateState() != null && request.getCreateState().equals(true)) {
							calculateAccessIfCreate(userPrivileges, holderItem, entity, allowed, componentAccessResponse);
						} else {
							calculateAccess(userPrivileges, holderItem, entity, allowed, componentAccessResponse);
						}
					}
				}
				componentsAccessResponse.add(componentAccessResponse);
				uicaMap.put(id, componentAccessResponse);
			}
			// Не передали список компонентов - обычно так
		} else {
			configMap.entrySet().forEach(e -> {

				UICAItemResponse componentAccessResponse = new UICAItemResponse();
				Set<UICAState> allowed = new HashSet<>();
				componentAccessResponse.setAccess(UICAState.HIDDEN);
				componentAccessResponse.setStatus(UICAStatus.NOT_FOUND);

				if(userAdminOrGod){
					componentAccessResponse.setAccess(UICAState.EDIT);
					componentAccessResponse.setStatus(UICAStatus.OK);
				}else {
					if (request.getCreateState() != null && request.getCreateState().equals(true)) {
						calculateAccessIfCreate(userPrivileges, e.getValue(), entity, allowed, componentAccessResponse);
					} else {
						calculateAccess(userPrivileges, e.getValue(), entity, allowed, componentAccessResponse);
					}
				}
				componentsAccessResponse.add(componentAccessResponse);
				uicaMap.put(e.getKey(), componentAccessResponse);
			});
		}
		return new EntityUICAResponse(entity, uicaMap);
	}

	private boolean isUserGod(final Set<Privilege4UICA> userPrivileges) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Privilege4UICA GOD = configuration.getPrivilegeService4UICA().getGodPrivilege();
		boolean isRoot = GOD == null ? false : userPrivileges.contains(GOD);
		return configuration.getUserService4UICA().isGod(auth) || isRoot;
	}

	private void calculateAccess(Set<Privilege4UICA> userPrivileges, Config4UICA.UICAHolderItem holderItem, Object gotEntity, Set<UICAState> allowed, UICAItemResponse componentAccessResponse) {
		Set<Privilege4UICA> privs = userPrivileges;
//		if(holderItem.getEntity() != null){
//			privs = getUserPrivileges(holderItem.getEntity());
//		}
//		final Set<Privilege> finalUserPrivileges = privs;
		commonPart(holderItem, gotEntity, allowed, userPrivileges);

		if (allowed.size() > 1) {
			if (allowed.contains(UICAState.EDIT)) {
				componentAccessResponse.setAccess(UICAState.EDIT);
				componentAccessResponse.setStatus(UICAStatus.OK);
			} else if (allowed.contains(UICAState.CREATE) && allowed.contains(UICAState.READ)) {
				componentAccessResponse.setAccess(UICAState.READ);
				componentAccessResponse.setStatus(UICAStatus.OK);
			} else {
				componentAccessResponse.setError("Ambiguous states [" + allowed.stream().map(e -> e.name()).collect(Collectors.joining(",")) + "]. Only one state allowed.");
				componentAccessResponse.setStatus(UICAStatus.ERROR);
			}
		}
		if (allowed.size() == 1) {
			if (allowed.iterator().next().equals(UICAState.CREATE)) {
				componentAccessResponse.setAccess(UICAState.HIDDEN);
				componentAccessResponse.setStatus(UICAStatus.OK);
			} else {
				componentAccessResponse.setAccess(allowed.iterator().next());
				componentAccessResponse.setStatus(UICAStatus.OK);
			}

		}
		if (allowed.size() == 0) {
			componentAccessResponse.setStatus(UICAStatus.OK);
		}
	}

	private void calculateAccessIfCreate(Set<Privilege4UICA> userPrivileges, Config4UICA.UICAHolderItem holderItem, Object gotEntity, Set<UICAState> allowed, UICAItemResponse componentAccessResponse) {
		Set<Privilege4UICA> privs = userPrivileges;
//		if(holderItem.getEntity() != null){
//			privs = getUserPrivileges(holderItem.getEntity());
//		}
//		final Set<Privilege> finalUserPrivileges = privs;
		commonPart(holderItem, gotEntity, allowed, privs);

		if (allowed.size() >= 1) {
			if (allowed.contains(UICAState.CREATE)) {
				componentAccessResponse.setAccess(UICAState.EDIT);
			} else {
				componentAccessResponse.setAccess(UICAState.READ);
			}
			componentAccessResponse.setStatus(UICAStatus.OK);
		}
		if (allowed.size() == 0) {
			componentAccessResponse.setStatus(UICAStatus.OK);
		}
	}

	private void commonPart(Config4UICA.UICAHolderItem holderItem, Object gotEntity, Set<UICAState> allowed, Set<Privilege4UICA> privs) {
		Map<UICAState, List<Boolean>> allowedMap = new HashMap<>();
		holderItem.getStates().forEach(state -> {
			final Set<Privilege4UICA> finalUserPrivileges;
			if (state.getEntity() != null) {
				finalUserPrivileges = getUserPrivileges(state.getEntity());
			} else {
				finalUserPrivileges = privs;
			}
			final AtomicBoolean isStateAllowed = new AtomicBoolean(false);
			List<Privilege4UICA> common = finalUserPrivileges.stream().filter(e -> state.getPrivileges().contains(e.getId())).collect(Collectors.toList());
			if (common.isEmpty()) {
				allowedCalc(allowedMap, isStateAllowed, state);
			} else {
				common.forEach(p -> {
					isStateAllowed.set(true);
					if (p.isUica()) {
						// Вычисляем скрипт, если передана сущность или выставлен флаг isCalculated.
						// Во втором случае скрипт вычисляется только по контексту (EmployeeResponseDTO).
						if (gotEntity != null || p.isCalculated()) {
							isStateAllowed.set(
									configuration.getJsEvaluator().execute(gotEntity, p.getStatementScript()));
						} else {
							isStateAllowed.set(false);
						}
					}
					allowedCalc(allowedMap, isStateAllowed, state);
				});
			}
		});
		allowed.addAll(allowedMap.entrySet().stream().filter(e -> !e.getValue().contains(false)).map(e -> e.getKey()).collect(Collectors.toSet()));
	}

	private void allowedCalc(Map<UICAState, List<Boolean>> allowedMap, AtomicBoolean isStateAllowed, Config4UICA.UIState state) {
		List<Boolean> bools = allowedMap.get(state.getState()) == null ? new ArrayList<>() : allowedMap.get(state.getState());
		bools.add(isStateAllowed.get());
		allowedMap.put(state.getState(), bools);
	}

	private Object getEntity(final Map<String, String> params, final String entityType) throws UICAException {
		if (params != null || !params.isEmpty()) {
			try {
				return configuration.getEntityUICAFactory().getService(entityType).get(params);
			} catch (Exception e) {
				log.error("", e);
				throw new UICAException(entityType + " with parameters: '" + params.entrySet().stream().map(o -> o.getKey() + "=" + o.getValue()).collect(Collectors.joining(";")) + "' not found or there was a problem trying to find it");
			}
		}
		return null;
	}

	private Set<Privilege4UICA> getUserPrivileges(final String entity) {
		Map<String, Set<Privilege4UICA>> userPrivilegesMap = configuration.getUserService4UICA().getUserPrivilegesByEntity(auth);
		Set<Privilege4UICA> set = userPrivilegesMap.get(entity);
		return set == null ? Collections.emptySet() : set;
	}

//	public UICAHolderDto getById(final String id) {
//		Optional<UICAHolderEntity> opt = repository.findById(id);
//		if (opt.isPresent()) {
//			return mapper.map(opt.get());
//		}
//		return null;
//	}

	private Config4UICA getConfig(final String id) throws UICAException {
		Holder4UICA holder4UICA = configuration.getHolderService4UICA().getById(id);
		if(holder4UICA != null){
			return holder4UICA.getConfig();
		}
		throw new UICAException("UICA code.entity with id '" + id + "' not found!");
	}

//	private Set<Privilege4UICA> getPrivilegesFromDB(List<String> privileges) {
//		Set<Privilege4UICA> response = new HashSet<>();
//		privileges.forEach(id -> {
//			Privilege4UICA privilege = configuration.getPrivilegeService4UICA().getById(id);
//			if (privilege != null) {
//				response.add(privilege);
//			}
//		});
//		return response;
//	}

}
