package code.impl.accessor.service;

import code.impl.accessor.repository.GrantAccessRepository;
import code.impl.accessor.repository.MethodAccessRepository;
import code.impl.accessor.repository.PrivilegeRepository;
import code.impl.accessor.repository.RoleRepository;
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
