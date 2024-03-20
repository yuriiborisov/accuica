package code.accessor.implementations.mapper;

import code.accessor.implementations.dto.response.GrantAccessResponse;
import code.accessor.implementations.entity.GrantAccessEntity;


public interface GrandAccessMapper {

	GrantAccessResponse map(GrantAccessEntity entity);
}
