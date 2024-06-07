package code.impl.uica.repository;

import code.impl.uica.entity.UICAHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UICARepository extends JpaRepository<UICAHolder, String> {
}