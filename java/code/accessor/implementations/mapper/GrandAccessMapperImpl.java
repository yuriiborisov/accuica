package code.accessor.implementations.mapper;

import code.accessor.core.Privilege4Access;
import code.accessor.implementations.dto.response.GrantAccessResponse;
import code.accessor.implementations.dto.response.PrivilegeResponse;
import code.accessor.implementations.entity.GrantAccessEntity;
import code.accessor.implementations.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class GrandAccessMapperImpl implements GrandAccessMapper {

    @Override
    public GrantAccessResponse map(GrantAccessEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GrantAccessResponse grantAccessResponse = new GrantAccessResponse();

        grantAccessResponse.setRoleId(entity.getRoleId());
        grantAccessResponse.setEntityType( entity.getEntityType() );
        grantAccessResponse.setPrivileges( PrivilegeEntityListToPrivilegeList( entity.getPrivileges() ) );

        return grantAccessResponse;
    }

    protected Privilege4Access PrivilegeEntityToPrivilege(PrivilegeEntity PrivilegeEntity) {
        if ( PrivilegeEntity == null ) {
            return null;
        }

        PrivilegeResponse privilege = new PrivilegeResponse();

        privilege.setId( PrivilegeEntity.getId() );
        privilege.setAlt( PrivilegeEntity.getAlt() );
        privilege.setDescription( PrivilegeEntity.getDescription() );
        privilege.setPriorityOrder( PrivilegeEntity.getPriorityOrder() );
        privilege.setCalculated( PrivilegeEntity.isCalculated() );
        privilege.setStatementScript( PrivilegeEntity.getStatementScript() );
        privilege.setFilterValue( PrivilegeEntity.getFilterValue() );
        privilege.setUica( PrivilegeEntity.isUica() );
        privilege.setFilter( PrivilegeEntity.isFilter() );

        return privilege;
    }

    protected List<Privilege4Access> PrivilegeEntityListToPrivilegeList(List<PrivilegeEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<Privilege4Access> list1 = new ArrayList<Privilege4Access>( list.size() );
        for ( PrivilegeEntity PrivilegeEntity : list ) {
            list1.add( PrivilegeEntityToPrivilege( PrivilegeEntity ) );
        }

        return list1;
    }
}
