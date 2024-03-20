package code.accessor.implementations.service;

import code.accessor.implementations.repository.GrantAccessRepository;
import code.accessor.implementations.repository.MethodAccessRepository;
import code.accessor.implementations.repository.PrivilegeRepository;
import code.accessor.implementations.repository.RoleRepository;
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
