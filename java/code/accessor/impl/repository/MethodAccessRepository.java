package code.accessor.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import code.accessor.impl.entity.MethodAccessEntity;
import code.accessor.impl.entity.PrivilegeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MethodAccessRepository extends JpaRepository<MethodAccessEntity,String> {
	List<MethodAccessEntity> findByKeyIn(Set<String> keys);
	Optional<MethodAccessEntity> findByKey(String id);

	@Query(nativeQuery = true, value = "select * from public.spg2_entity_access ea"
			+ "left join public.spg2_entity_access_privilege eap on eap.required_access_id = ea.key "
			+ "left join public.privilege p on p.id = eap.privilege_id "
			+ "where ea.method = :method and ea.entity_type = :entityType")
	List<PrivilegeEntity> findByMethodAndEntity(String method, String entityType);
}