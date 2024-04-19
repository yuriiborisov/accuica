package code.impl.accessor.service;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.exception.RoleException;
import code.accessor.core.code.service.RoleService4Access;
import code.impl.accessor.dto.request.Role4AccessRequest;
import code.impl.accessor.dto.request.Role4AccessRequestV2;
import code.impl.accessor.entity.GrantAccessEntity;
import code.impl.accessor.entity.GrantAccessEntityPk;
import code.impl.accessor.entity.PrivilegeEntity;
import code.impl.accessor.entity.RoleEntity;
import code.impl.accessor.mapper.RoleMapperImpl;
import code.impl.accessor.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends Role4AccessBaseAbstract implements RoleService4Access {
	private final AccessRepository $;
	private final RoleMapperImpl mapper;
	private final RoleRepository roleRepository;

	public RoleServiceImpl(final AccessRepository $, final RoleMapperImpl mapper, PrivilegeServiceImpl privilegeService,
						   RoleRepository roleRepository) {
		super(privilegeService);
		this.$ = $;
		this.mapper = mapper;
		this.roleRepository = roleRepository;
	}
	@Override
	public Role4AccessResponse update(Role4AccessRequestV2 requestV2){
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
			if(privilegesMapFromRoleDb.containsKey(e.getEntityType())){
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

	@Override
	public Role4AccessResponse updateInfo(Role4AccessRequest request) {
		RoleEntity roleEntity = $.getRolesRepo().findById(request.getId()).orElseThrow(() -> new RoleException("Role with id: '" + request.getId() + "' not found!"));
		RoleEntity parentRoleEntity = $.getRolesRepo().findById(request.getParentId()).orElseThrow(() -> new RoleException("Role with id: '" + request.getId() + "' not found!"));
		roleEntity.setName(request.getName());
		roleEntity.setParent(parentRoleEntity);
		roleEntity.setDescription(request.getDescription());
		roleEntity.setSortOrder(request.getSortOrder());
		RoleEntity saved = $.getRolesRepo().save(roleEntity);
		return mapper.map(saved);
	}

	@Override
	public Role4AccessResponse getById(final String id){
		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() ->  new RoleException("Role with id: '" + id + "' not found!"));
		return mapper.map(roleEntity);
	}

	@Override
	public void delete(String id) throws RoleException {
		RoleEntity roleEntity = $.getRolesRepo().findById(id).orElseThrow(() -> new RoleException("Role with id: '" + id + "' not found!"));
        $.getRolesRepo().delete(roleEntity);
	}

	@Override
	@Transactional
	public Role4AccessResponse create(Role4AccessRequest request) throws RoleException {

		Optional<RoleEntity> optional = $.getRolesRepo().findById(request.getId());
		if(!optional.isPresent()){
			RoleEntity role = new RoleEntity();
			role.setId(request.getId());
			role.setParent(getParent(request.getParentId()));
			role.setName(request.getName());
			role.setDescription(request.getDescription());
			role.setSortOrder(request.getSortOrder());
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

	@Override
	public List<Role4AccessResponse> getAll(){
        var res = $.getRolesRepo().findAll().stream()
                .map(e -> mapper.map(e))
                .sorted((role1, role2) ->{
                    var compareByOrder = Integer.compare(role1.getSortOrder(),role2.getSortOrder());
                    return compareByOrder==0 ? StringUtils.compareIgnoreCase(role1.getName(),role2.getName()): compareByOrder;
                } )
                .collect(Collectors.toList());
        return res;
	}
}
