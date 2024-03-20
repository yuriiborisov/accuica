package code.accessor.implementations.repository;

import code.accessor.implementations.entity.PrivilegeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, String> {
	@Query(nativeQuery = true, value = "SELECT * FROM public.privilege t WHERE t.id in :ids ")
	List<PrivilegeEntity> findByIdIsIn(@Param("ids") List<String> ids);

	Optional<PrivilegeEntity> findById(String id);
}
