package code.accessor.impl.service;

import code.accessor.impl.repository.GrantAccessRepository;
import code.accessor.impl.repository.MethodAccessRepository;
import code.accessor.impl.repository.PrivilegeRepository;
import code.accessor.impl.repository.RoleRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AccessRepository {
	private final GrantAccessRepository grantsRepo;
	private final MethodAccessRepository methodsRepo;
	private final PrivilegeRepository privilegesRepo;
	private final RoleRepository rolesRepo;

	public AccessRepository(final GrantAccessRepository grantsRepo,final MethodAccessRepository methodsRepo,
			final PrivilegeRepository privilegesRepo, final RoleRepository rolesRepo) {
		this.grantsRepo = grantsRepo;
		this.methodsRepo = methodsRepo;
		this.privilegesRepo = privilegesRepo;
		this.rolesRepo = rolesRepo;
	}
}
