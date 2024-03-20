package code.accessor.implementations.repository;

import code.accessor.implementations.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
	Optional<RoleEntity> findById(String id);
	Page<RoleEntity> findAllByIdIn(Set<String> ids, Pageable pageable);

	Page<RoleEntity> findAllByName(String name, Pageable pageable);

	Page<RoleEntity> findAllByNameLike(String nameLike, Pageable pageable);

	long countByIdIn(Set<String> ids);

	long countByName(String name);

	long countByNameLike(String nameLike);
}