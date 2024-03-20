package code.accessor.implementations.mapper;

import code.accessor.core.Privilege4Access;
import code.accessor.implementations.entity.PrivilegeEntity;


public interface PrivilegeMapper {
    //Имя методов легаси
   Privilege4Access map(PrivilegeEntity entity);
//   PrivilegeEntity map(Privilege4Access privilege);
}
