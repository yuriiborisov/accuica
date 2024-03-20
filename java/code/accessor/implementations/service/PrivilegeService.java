package code.accessor.implementations.service;

import code.accessor.core.Privilege4Access;
import code.accessor.core.PrivilegeService4Access;
import code.accessor.implementations.entity.PrivilegeEntity;
import code.accessor.implementations.mapper.PrivilegeMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeService extends EntityAccessBaseService implements PrivilegeService4Access {
	private final AccessRepository $;
	private final PrivilegeMapper mapper;
//	private final PrivilegeMethodAccessor code.accessor;

	public PrivilegeService(final AccessRepository $, PrivilegeMapper mapper) {
		super($ );
		this.$ = $;
		this.mapper = mapper;
//		this.code.accessor = code.accessor;
		}
//
//	public Privilege create(Privilege privilege) throws PrivilegeException, AccessDeniedException {
//		code.accessor.create();
//		Privilege response = getById(privilege.getId());
//		if(response == null){
//			SPG2PrivilegeEntity entity = mapper.map(privilege);
//			entity = $.getPRepository().save(entity);
//			return mapper.map(entity);
//		}
//		throw new PrivilegeException("Privilege with id '"+privilege.getId()+"' already exists!");
//	}
//
//	public Privilege update(Privilege privilege) throws PrivilegeException, AccessDeniedException {
//		code.accessor.edit();
//		Privilege response = getById(privilege.getId());
//		if(response != null){
//			SPG2PrivilegeEntity entity = mapper.map(privilege);
//			entity = $.getPRepository().save(entity);
//			return mapper.map(entity);
//		}
//		throw new PrivilegeException("Privilege with id '"+privilege.getId()+"' not found!");
//	}

	public Privilege4Access getById(final String id) {
		Optional<PrivilegeEntity> optional = $.getPrivilegesRepo().findById(id);
		if(optional.isPresent()){
			return mapper.map(optional.get());
		}
		return null;
	}

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
//	public void delete(final String id) throws AccessDeniedException {
//		code.accessor.delete();
//		$.getPRepository().deleteById(id);
//	}
}
