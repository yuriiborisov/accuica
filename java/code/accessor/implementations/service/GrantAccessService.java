package code.accessor.implementations.service;

import code.accessor.core.GrantAccess4Access;
import code.accessor.core.GrantAccessService4Access;
import code.accessor.core.Privilege4Access;
import code.accessor.implementations.mapper.GrandAccessMapper;
import org.springframework.stereotype.Service;

@Service
public class GrantAccessService extends EntityAccessBaseService implements GrantAccessService4Access {
	private final AccessRepository $;
	private GrandAccessMapper mapper;
	public GrantAccessService(final AccessRepository $, final GrandAccessMapper mapper) {
		super($);
		this.$ = $;
		this.mapper = mapper;
	}
//
//	@Override
//	public GrantAccess4Access map(Object grantAccessEntity) {
//		return mapper.map((GrantAccessEntity)grantAccessEntity);
//	}
//
//	@Override
//	public GrantAccess4Access getById(String id) {
//		return null;
//	}

	@Override
	public boolean hasPrivilege(GrantAccess4Access grantAccess4Access, Privilege4Access privilegeId) {
		return GrantAccessService4Access.super.hasPrivilege(grantAccess4Access, privilegeId);
	}
}
