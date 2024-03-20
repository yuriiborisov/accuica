package code.uica.code.repository;

import code.uica.impl.dto.UICAHolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UICARepository extends JpaRepository<UICAHolderEntity, String> {
}