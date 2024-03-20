package code.accessor.impl.mapper;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.dto.GrantAccess4Access;
import code.accessor.impl.dto.response.Role4AccessResponseImpl;
import code.accessor.impl.entity.GrantAccessEntity;
import code.accessor.impl.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RoleMapperImpl {

    @Autowired
    private GrandAccessMapperImpl grandAccessMapperImpl;

    public Role4AccessResponse map(RoleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Role4AccessResponseImpl roleResponse = new Role4AccessResponseImpl();

        roleResponse.setId( entity.getId() );
        roleResponse.setName( entity.getName() );
        roleResponse.setDescription( entity.getDescription() );
        roleResponse.setGrantAccesses( GrantAccessEntityListToGrantAccessResponseList( entity.getGrantAccesses() ) );
        roleResponse.setParent( map( entity.getParent() ) );
        roleResponse.setSortOrder(entity.getSortOrder());

        return roleResponse;
    }
    protected List<GrantAccess4Access> GrantAccessEntityListToGrantAccessResponseList(List<GrantAccessEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<GrantAccess4Access> list1 = new ArrayList<GrantAccess4Access>( list.size() );
        for ( GrantAccessEntity GrantAccessEntity : list ) {
            list1.add( grandAccessMapperImpl.map( GrantAccessEntity ) );
        }

        return list1;
    }
}
