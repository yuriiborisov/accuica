package code.accessor.impl.service;

import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.exception.PrivilegeException;
import code.accessor.core.code.service.PrivilegeService4Access;
import code.accessor.impl.entity.PrivilegeEntity;
import code.accessor.impl.mapper.PrivilegeMapperImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl extends BaseService4AccessAbstract implements PrivilegeService4Access {
	private final AccessRepository $;
	private final PrivilegeMapperImpl mapper;

	public PrivilegeServiceImpl(final AccessRepository $, PrivilegeMapperImpl mapper) {
		super($ );
		this.$ = $;
		this.mapper = mapper;
		}

	@Override
	public Privilege4Access create(Privilege4Access privilege) throws PrivilegeException {
		Privilege4Access response = getById(privilege.getId());
		if(response == null){
			PrivilegeEntity entity = mapper.map(privilege);
			entity = $.getPrivilegesRepo().save(entity);
			return mapper.map(entity);
		}
		throw new PrivilegeException("Privilege with id '"+privilege.getId()+"' already exists!");
	}

	@Override
	public Privilege4Access update(Privilege4Access privilege) throws PrivilegeException {
		Privilege4Access response = getById(privilege.getId());
		if(response != null){
			PrivilegeEntity entity = mapper.map(privilege);
			entity.setId(response.getId());
			entity = $.getPrivilegesRepo().save(entity);
			return mapper.map(entity);
		}
		throw new PrivilegeException("Privilege with id '"+privilege.getId()+"' not found!");
	}

	@Override
	public Privilege4Access getById(final String id) {
		Optional<PrivilegeEntity> optional = $.getPrivilegesRepo().findById(id);
		if(optional.isPresent()){
			return mapper.map(optional.get());
		}
		return null;
	}

	@Override
	public List<Privilege4Access> getAll() {
		List<PrivilegeEntity> response = $.getPrivilegesRepo().findAll();
		return response.stream().map(e -> mapper.map(e)).sorted(Comparator.comparing(Privilege4Access::getId)).collect(Collectors.toList());}

	@Override
	public Privilege4Access getGodPrivilege() {
		return null;
	}

//	@Transactional
//	public ResponseEntity impord(@RequestBody List<Privilege> privileges) throws AccessDeniedException, PrivilegeException {
//		code.accessor.create();
//		for( Privilege privilege : privileges){
//			Privilege response = getById(privilege.getId());
//			if(response == null){
//				$.getPRepository().save(mapper.map(privilege));
//			}
//			throw new PrivilegeException("Privilege with id '"+privilege.getId()+"' already exists!");
//		}
//		return ResponseEntity.ok().body("");
//	}
//
//	public List<Privilege> getAll() throws AccessDeniedException {
//		code.accessor.read();
//		return $.getPRepository().findAll().stream().map(e->mapper.map(e)).collect(Collectors.toList());
//	}
//
@Override
	public void delete(final String id){
		$.getPrivilegesRepo().deleteById(id);
	}
}
