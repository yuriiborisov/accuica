package code.accessor.implementations.repository;


import code.accessor.implementations.entity.GrantAccessEntity;
import code.accessor.implementations.entity.GrantAccessEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface GrantAccessRepository extends JpaRepository<GrantAccessEntity, GrantAccessEntityPk> {
	Optional<GrantAccessEntity> findById(GrantAccessEntityPk id);
	@Query(nativeQuery = true, value = "select * from public.grant_access ga "
			+ "left join public.role r on ga.role_id = r.id "
			+ "where r.id = :id")
	List<GrantAccessEntity> findByRoleId(String id);
}