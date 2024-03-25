package code.impl.accessor.service;

import code.accessor.core.code.*;
import code.accessor.core.code.dto.request.Method4AccessRequest;
import code.accessor.core.code.dto.response.Method4AccessResponse;
import code.accessor.core.code.service.MethodAccessService4Access;
import code.accessor.core.update.Entity4AccessConfig;
import code.accessor.core.update.Method4AccessConfig;
import code.impl.accessor.entity.MethodAccessEntity;
import code.impl.accessor.mapper.MethodAccessMapperImpl;
import code.impl.accessor.mapper.PrivilegeMapperImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MethodAccessServiceImpl extends BaseService4AccessAbstract implements MethodAccessService4Access, PrivilegesByKeyGetter {
	private final AccessRepository $;
	private final MethodAccessMapperImpl mapper;
	private final PrivilegeMapperImpl privilegeMapper;

	public MethodAccessServiceImpl(final AccessRepository $, MethodAccessMapperImpl mapper, PrivilegeMapperImpl privilegeMapper) {
		super($);
		this.$ = $;
		this.mapper = mapper;
		this.privilegeMapper = privilegeMapper;
	}

	@Override
	public Method4AccessResponse getByKey(String id){
		Optional<MethodAccessEntity> optional = $.getMethodsRepo().findByKey(id);
		if(optional.isPresent()){
			return mapper.map(optional.get());
		}
		return null;
	}

	@Override
	public List<Method4AccessResponse> getByKeys(Set<String> ids){
		List<MethodAccessEntity> result = $.getMethodsRepo().findByKeyIn(ids);
		return result.stream().map(mapper::map).collect(Collectors.toList());
	}

	@Override
	public Method4AccessResponse create(Method4AccessRequest request) {
		MethodAccessEntity entity = mapper.map(request);
		MethodAccessEntity saved = $.getMethodsRepo().saveAndFlush(entity);
		return mapper.map(saved);
	}

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

	@Override
	public List<Method4AccessResponse> getAll() {
		return  $.getMethodsRepo().findAll().stream().map(m -> mapper.map(m)).collect(Collectors.toList());
	}

	@Override
	public List<Method4AccessConfig> getMethods() {
		return  $.getMethodsRepo().findAll().stream().map(m -> mapper.map_(m)).collect(Collectors.toList());
	}

	@Override
	public List<Entity4AccessConfig> getMethodsByEntity(String entity) {
		return null;
	}
}
