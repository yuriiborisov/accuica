package code.accessor.implementations.mapper;


import code.accessor.implementations.dto.response.RoleResponse;
import code.accessor.implementations.entity.RoleEntity;

public interface RoleMapper {
    //дополнительно определяю, что sortOrde == null будет 0l. при необходимости можно переделать в бесконечность

    default Integer setOrder(Integer order){
      return order==null? 0:order;
    };

    RoleResponse map(RoleEntity entity);
}
