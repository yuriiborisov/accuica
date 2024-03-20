package code.accessor.implementations.mapper;

import code.accessor.core.GrantAccess4Access;
import code.accessor.implementations.dto.request.RoleRequest;
import code.accessor.implementations.dto.response.RoleResponse;
import code.accessor.implementations.entity.GrantAccessEntity;
import code.accessor.implementations.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private GrandAccessMapper grandAccessMapper;

    @Override
    public RoleResponse map(RoleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setId( entity.getId() );
        roleResponse.setName( entity.getName() );
        roleResponse.setDescription( entity.getDescription() );
        roleResponse.setGrantAccesses( GrantAccessEntityListToGrantAccessResponseList( entity.getGrantAccesses() ) );
        roleResponse.setParent( map( entity.getParent() ) );

        roleResponse.setPriority( setOrder(entity.getSortOrder()) );

        return roleResponse;
    }
    protected List<GrantAccess4Access> GrantAccessEntityListToGrantAccessResponseList(List<GrantAccessEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<GrantAccess4Access> list1 = new ArrayList<GrantAccess4Access>( list.size() );
        for ( GrantAccessEntity GrantAccessEntity : list ) {
            list1.add( grandAccessMapper.map( GrantAccessEntity ) );
        }

        return list1;
    }
}
