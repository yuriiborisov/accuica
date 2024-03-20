package code.accessor.implementations.mapper;

import code.accessor.core.DefaultAccessInfo;
import code.accessor.core.MethodAccess4Access;
import code.accessor.implementations.dto.request.MethodAccessRequest;
import code.accessor.implementations.entity.MethodAccessEntity;

public interface MethodAccessMapper {
    MethodAccess4Access map(MethodAccessEntity entity);
    MethodAccessEntity map(MethodAccessRequest request);
    MethodAccess4Access map(DefaultAccessInfo info);


}
