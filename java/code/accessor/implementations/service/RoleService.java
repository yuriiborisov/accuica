package code.accessor.implementations.service;

import code.accessor.core.Role4Access;
import code.accessor.core.RoleService4Access;
import code.accessor.core.exception.RoleException;
import code.accessor.implementations.dto.EntityPrivilegeMatrix;
import code.accessor.implementations.dto.PrivilegeEntityMatrix;
import code.accessor.implementations.dto.request.RoleRequest;
import code.accessor.implementations.dto.request.RoleRequestV2;
import code.accessor.implementations.dto.response.RoleResponse;
import code.accessor.implementations.entity.GrantAccessEntity;
import code.accessor.implementations.entity.GrantAccessEntityPk;
import code.accessor.implementations.entity.PrivilegeEntity;
import code.accessor.implementations.entity.RoleEntity;
import code.accessor.implementations.mapper.PrivilegeMapper;
import code.accessor.implementations.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService extends EntityAccessBaseService implements RoleService4Access {
	private final AccessRepository $;
	private final RoleMapper mapper;
	private final PrivilegeMapper mapperPriv;
	private final MethodAccessService methodService;
//	private final RoleMethodAccessor code.accessor;
	private final PrivilegeMapper privilegeMapper;

	public RoleService(final AccessRepository $, final RoleMapper mapper, PrivilegeMapper mapperPriv, final MethodAccessService methodService, PrivilegeMapper privilegeMapper) {
		super($);
		this.$ = $;
		this.mapper = mapper;
		this.mapperPriv = mapperPriv;
		this.methodService = methodService;
//		this.code.accessor = code.accessor;
		this.privilegeMapper = privilegeMapper;
	}
	public Role4Access update(RoleRequestV2 requestV2){
//		code.accessor.read();
		RoleEntity roleEntity = $.getRolesRepo().findById(requestV2.getId()).orElseThrow(() -> new RoleException("Role with id: '" + requestV2.getId() + "' not found!"));
		Map<String, List<String>> privilegesMapFromRoleDb = roleEntity.getGrantAccesses().stream()
				.collect(Collectors.toMap(k -> k.getEntityType(), v-> v.getPrivilegeIds(), (a,b) -> b));
		Map<String, GrantAccessEntity> grantAccessMapFromRoleDb = roleEntity.getGrantAccesses().stream()
				.collect(Collectors.toMap(k -> k.getEntityType(), v-> v, (a,b) -> b));
		Map<String, List<String>> mapFromRequest = requestV2.getGrantAccesses().stream()
				.collect(Collectors.toMap(k -> k.getEntityType(), v-> v.getPrivilegeIds(), (a,b) -> b));
		List<GrantAccessEntity> grantAccessList = roleEntity.getGrantAccesses();
		List<GrantAccessEntity> grantAccessNewList = new ArrayList<>();
		requestV2.getGrantAccesses().forEach(e -> {
//			List<PrivilegeEntity> privileges = e.getPrivileges();
//			List<String> privs = requestV2.getGrantAccesses().stream().map(p-> p.getId()).collect(Collectors.toList());
			if(privilegesMapFromRoleDb.containsKey(e.getEntityType())){
//				List<String> idsToAdd = CollectionUtils.disjunction(mapFromRequest.get(e.getEntityType()), mapFromRoleDb.get(e.getEntityType())).stream().toList();
				List<PrivilegeEntity> privsToAdd =$.getPrivilegesRepo().findByIdIsIn(mapFromRequest.get(e.getEntityType()));
				grantAccessMapFromRoleDb.get(e.getEntityType()).setPrivileges(privsToAdd);
			}else{
				List<PrivilegeEntity> privsToAdd = $.getPrivilegesRepo().findByIdIsIn(e.getPrivilegeIds());
				GrantAccessEntity grantAccessEntity = new GrantAccessEntity();
				grantAccessEntity.setId(new GrantAccessEntityPk(requestV2.getId(), e.getEntityType()));
				grantAccessEntity.setPrivileges(privsToAdd);
				grantAccessNewList.add(grantAccessEntity);
			}
		});
		grantAccessList.addAll(grantAccessNewList);
		RoleEntity saved = $.getRolesRepo().save(roleEntity);
		return mapper.map(saved);
	}

//	public List<EntityPrivilegeMatrix> getMatrix(String id){
//		List<EntityPrivilegeMatrix> result = new ArrayList<>();
//		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() ->  new RoleException("Role with id: '" + id + "' not found!"));
//		roleEntity.getGrantAccesses().forEach(ga -> {
//			result.add(new EntityPrivilegeMatrix(ga.getEntityType(), ga.getPrivilegeIds()));
//		});
//
//		return result;
//	}

	@Override
	public Role4Access getById(final String id){
		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() ->  new RoleException("Role with id: '" + id + "' not found!"));
		return mapper.map(roleEntity);
	}
	public List<PrivilegeEntityMatrix> getMatrixT(String id){
		List<PrivilegeEntityMatrix> result = new ArrayList<>();
		Map<PrivilegeEntity, List<String>> map = new HashMap<>();
		List<PrivilegeEntity> allPrivileges = $.getPrivilegesRepo().findAll();
		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() ->  new RoleException("Role with id: '" + id + "' not found!"));

		roleEntity.getGrantAccesses().forEach(ga -> {
			ga.getPrivileges().forEach(p-> {
				List<String> entities = map.get(p) == null ? new ArrayList<>() : map.get(p);
				entities.add(ga.getEntityType());
				map.put(p,entities);
			});
		});

		map.entrySet().forEach(item -> {
			result.add(new PrivilegeEntityMatrix(mapperPriv.map(item.getKey()), item.getValue().stream().distinct().collect(Collectors.toList())));
		});
		return allPrivileges.stream().map(p ->
				 new PrivilegeEntityMatrix(mapperPriv.map(p), !map.containsKey(p) ? new ArrayList<>() : map.get(p).stream().distinct().collect(Collectors.toList()))
		).collect(Collectors.toList());

//		return result;
	}

	public void delete(String id) throws RoleException {
		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() -> new RoleException("Role with id: '" + id + "' not found!"));
        $.getRolesRepo().delete(roleEntity);
	}
//

	@Transactional
	public RoleResponse update(RoleRequest request) throws RoleException {

		Optional<RoleEntity> optional = $.getRolesRepo().findById(request.getId());
		if(optional.isPresent()){
			RoleEntity role = optional.get();
			role.setParent(getParent(request.getParentId()));
			role.setName(request.getName());
			role.setDescription(request.getDescription());
			role.setSortOrder(request.getPriority());
			return mapper.map($.getRolesRepo().save(role));
		}
		throw new RoleException("Role with id: '" + request.getId() + "' doesn't exist!");
	}

	@Transactional
	public RoleResponse create(RoleRequest request) throws RoleException {

		Optional<RoleEntity> optional = $.getRolesRepo().findById(request.getId());
		if(!optional.isPresent()){
			RoleEntity role = new RoleEntity();
			role.setId(request.getId());
			role.setParent(getParent(request.getParentId()));
			role.setName(request.getName());
			role.setDescription(request.getDescription());
			role.setSortOrder(request.getPriority());
			RoleEntity saved = $.getRolesRepo().save(role);
			saved.setGrantAccesses(new ArrayList<>());
			return mapper.map($.getRolesRepo().save(saved));
		}
		throw new RoleException("Role with id: '" + request.getId() + "' already exist!");
	}
	private RoleEntity getParent(String id){
		if(id == null){
			return null;
		}
		Optional<RoleEntity> optional = $.getRolesRepo().findById(id);
        return optional.orElse(null);
    }
//		private List<SPG2GrantAccessEntity> getGrantAccesses(SPG2RoleEntity role, List<GrantAccessRequest> accesses){
//		List<SPG2GrantAccessEntity> toDelete = $.getGaRepository().findByRoleId(role.getId());
//		$.getGaRepository().deleteAll(toDelete);
//		return accesses.stream().map(e->{
//			SPG2GrantAccessEntity entity = new SPG2GrantAccessEntity();
//			entity.setId(new SPG2GrantAccessEntityPk(role.getId(), e.getEntity()));
//			entity.setPrivileges(getPrivileges(e.getPrivilegeIds()));
//			return $.getGaRepository().save(entity);
//		}).collect(Collectors.toList());
//	}

//
//	@Transactional
//	public RoleResponse update(RoleRequest request) throws RoleException, AccessDeniedException {
//		code.accessor.edit();
//		RoleResponse response = findById(request.getId());
//		if(response != null){
//			SPG2RoleEntity role = new SPG2RoleEntity();
//			role.setId(request.getId());
//			role.setParent(getParent(request.getParentId()));
//			role.setName(request.getName());
//			role.setDescription(request.getDescription());
//			SPG2RoleEntity saved = $.getRRepository().save(role);
//			saved.setGrantAccesses(getGrantAccesses(role, request.getGrantAccesses()));
//			return mapper.map($.getRRepository().save(saved));
//		}
//		throw new RoleException("Role with id: '" + request.getId() + "' not found!");
//	}
//
//
//	public void getRolePrivileges(String id, Map<Entities, Set<Privilege>> map){
//		getPrivileges(id, map);
//	}
//

//
//
//	private void getPrivileges(String id, Map<Entities, Set<Privilege>> map){
//
//			if(id != null) {
//				SPG2RoleEntity role = getRole(id);
//				if(role != null){
//					role.getGrantAccesses().forEach(ga -> {
//						Entities ndx = ga.getEntity();
//                        map.computeIfAbsent(ndx, k -> new HashSet<>());
//						map.get(ga.getEntity()).addAll(ga.getPrivileges().stream().map(privilegeMapper::map).collect(Collectors.toSet()));
//						getPrivileges(role.getParent() == null ? null : role.getParent().getId(), map);
//					});
//				}
//			}
//	}
//
//	private List<SPG2PrivilegeEntity> getPrivileges(List<String> ids){
//		List<SPG2PrivilegeEntity> result = new ArrayList<>();
//		ids.forEach(id -> {
//			Optional<SPG2PrivilegeEntity> optional = $.getPRepository().findById(id);
//            optional.ifPresent(result::add);
//		});
//		return result;
//	}
//
//	private SPG2RoleEntity getParent(String id){
//		if(id == null){
//			return null;
//		}
//		Optional<SPG2RoleEntity> optional = $.getRRepository().findById(id);
//        return optional.orElse(null);
//    }
//
//	public RoleResponse findById(String id){
//		Optional<SPG2RoleEntity> optional = $.getRRepository().findById(id);
//        return optional.map(mapper::map).orElse(null);
//    }
//
	public List<RoleResponse> getAll(){
        var res = $.getRolesRepo().findAll().stream()
                .map(e -> mapper.map(e))
                .sorted((role1, role2) ->{
                    var compareByOrder = Integer.compare(role1.getPriority(),role2.getPriority());
                    return compareByOrder==0 ? StringUtils.compareIgnoreCase(role1.getName(),role2.getName()): compareByOrder;
                } )
                .collect(Collectors.toList());
        return res;
	}
//
//	public Stream<RoleResponse> getAllByIds(Set<String> ids, int start, int count){
//		return $.getRRepository().findAllByIdIn(ids, pageRequest(start,count)).stream().map(mapper::map);
//	}
//
//	public long getCountByIds(Set<String> ids){
//		return $.getRRepository().countByIdIn(ids);
//	}
//
//	public Stream<RoleResponse> getAll(int start, int count){
//		return $.getRRepository().findAll(pageRequest(start,count)).stream().map(mapper::map);
//	}
//
//	public long getAllCount(){
//		return $.getRRepository().count();
//	}
//
//	public Stream<RoleResponse> getAllByName(String name, int start, int count){
//		return $.getRRepository().findAllByName(name, pageRequest(start,count)).stream().map(mapper::map);
//	}
//
//	public long getCountByName(String name){
//		return $.getRRepository().countByName(name);
//	}
//
//	public Stream<RoleResponse> getAllByNameLike(String nameLike, int start, int count){
//		return $.getRRepository().findAllByNameLike(nameLike, pageRequest(start,count)).stream().map(mapper::map);
//	}
//
//	public long getCountByNameLike(String nameLike){
//		return $.getRRepository().countByNameLike(nameLike);
//	}
//
//	private static PageRequest pageRequest(int start, int count){
//		return PageRequest.of(start,count, Sort.by("name").ascending());
//	}
//
//	@Override
//	public Role4Access getRole(Object id) {
//		Optional<SPG2RoleEntity> optional = $.getRRepository().findById(id);
//		return optional.orElse(null);
//	}
}
