package code.accessor.impl.mapper;

import code.accessor.core.code.Privilege4Access;
import code.accessor.impl.dto.response.GrantAccessResponseImpl;
import code.accessor.impl.dto.Privilege4AccessImpl;
import code.accessor.impl.entity.GrantAccessEntity;
import code.accessor.impl.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class GrandAccessMapperImpl {

    public GrantAccessResponseImpl map(GrantAccessEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GrantAccessResponseImpl grantAccessResponse = new GrantAccessResponseImpl();

        grantAccessResponse.setRoleId(entity.getRoleId());
        grantAccessResponse.setEntityType( entity.getEntityType() );
        grantAccessResponse.setPrivileges( PrivilegeEntityListToPrivilegeList( entity.getPrivileges() ) );

        return grantAccessResponse;
    }

    protected Privilege4Access PrivilegeEntityToPrivilege(PrivilegeEntity PrivilegeEntity) {
        if ( PrivilegeEntity == null ) {
            return null;
        }

        Privilege4AccessImpl privilege = new Privilege4AccessImpl();

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
