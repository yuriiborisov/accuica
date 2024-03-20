package code.accessor.implementations.service;

import code.accessor.core.*;
import code.accessor.implementations.dto.request.MethodAccessRequest;
import code.accessor.implementations.entity.MethodAccessEntity;
import code.accessor.implementations.mapper.MethodAccessMapper;
import code.accessor.implementations.mapper.PrivilegeMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MethodAccessService extends EntityAccessBaseService implements MethodAccessService4Access, PrivilegesByKeyGetter {
	private final AccessRepository $;
	private final MethodAccessMapper mapper;
	private final PrivilegeMapper privilegeMapper;

	public MethodAccessService(final AccessRepository $, MethodAccessMapper mapper, PrivilegeMapper privilegeMapper) {
		super($);
		this.$ = $;
		this.mapper = mapper;
		this.privilegeMapper = privilegeMapper;
	}

	public MethodAccess4Access getByKey(String id){
		Optional<MethodAccessEntity> optional = $.getMethodsRepo().findByKey(id);
		if(optional.isPresent()){
			return mapper.map(optional.get());
		}
		return null;
	}

	public List<MethodAccess4Access> getByKeys(Set<String> ids){
		List<MethodAccessEntity> result = $.getMethodsRepo().findByKeyIn(ids);
		return result.stream().map(mapper::map).collect(Collectors.toList());
	}

	@Override
	public MethodAccess4Access create(Object request) {
		MethodAccessEntity entity = mapper.map((MethodAccessRequest) request);
		MethodAccessEntity saved = $.getMethodsRepo().saveAndFlush(entity);
		return mapper.map(saved);
	}

	@Override
	public MethodAccess4Access map(DefaultAccessInfo info) {
		return mapper.map(info);
	}
//
//
//	public MethodAccessResponse update(MethodAccessRequest src){
////		SPG2MethodAccessEntity entity = new SPG2MethodAccessEntity();
//		Optional<SPG2MethodAccessEntity> optional = $.getMaRepository().findByKey(src.getKey());
//		if(optional.isPresent()) {
//			SPG2MethodAccessEntity entity = optional.get();
//			entity.setKey(src.getKey());
//			entity.setEntity(src.getEntity());
//			entity.setMethod(src.getMethod());
//			entity.setDescription(src.getDescription());
//			entity.setHumanName(src.getHumanName());
//			entity.setPrivileges($.getPRepository().findByIdIsIn(src.getPrivilegeIds()));
//			//		return fromEntity($.getMaRepository().save(entity));
//			return mapper.map($.getMaRepository().save(entity));
//		}
//		throw new NotFoundException("Method access not found");
//	}

	public List<Privilege4Access> getPrivileges(String key){
		Optional<MethodAccessEntity> optional = $.getMethodsRepo().findByKey(key);
		if(optional.isPresent()){
			MethodAccessEntity methodAccessEntity = optional.get();
			return methodAccessEntity.getPrivileges().stream()
					.map(privilegeMapper::map)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

//	@Transactional
//	public ResponseEntity impord(@RequestBody List<MethodAccessRequest> request){
//		for( MethodAccessRequest method : request){
//				create(method);
//		}
//		return ResponseEntity.ok().body("OK");
//	}
//
//	public List<MethodAccessResponse> getAll(){
//		return $.getMaRepository().findAll().stream()
//				.map(mapper::map)
//				.collect(Collectors.toList());
//	}
}
