package code.accessor.implementations.mapper;

import code.accessor.core.DefaultAccessInfo;
import code.accessor.core.MethodAccess4Access;
import code.accessor.core.impl.MethodAccess4AccessImpl;
import code.accessor.core.Privilege4Access;
import code.accessor.implementations.dto.request.MethodAccessRequest;
import code.accessor.implementations.entity.MethodAccessEntity;
import code.accessor.implementations.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MethodAccessMapperImpl implements MethodAccessMapper {

    private final PrivilegeMapper privilegeMapper;

    public MethodAccessMapperImpl(PrivilegeMapper privilegeMapper) {
        this.privilegeMapper = privilegeMapper;
    }

    @Override
    public MethodAccess4AccessImpl map(MethodAccessEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MethodAccess4AccessImpl methodAccess4Access = new MethodAccess4AccessImpl();

       methodAccess4Access.setEntityType( entity.getEntityType() );
       methodAccess4Access.setKey( entity.getKey() );
       methodAccess4Access.setHumanName( entity.getHumanName() );
       methodAccess4Access.setDescription( entity.getDescription() );
       methodAccess4Access.setMethod( entity.getMethod() );
       methodAccess4Access.setPrivileges( PrivilegeEntityListToPrivilegeList(entity.getPrivileges()));

        return methodAccess4Access;
    }

    @Override
    public MethodAccessEntity map(MethodAccessRequest request) {
        return null;
    }

    @Override
    public MethodAccess4Access map(DefaultAccessInfo info) {
        return null;
    }

    protected Privilege4Access PrivilegeEntityToPrivilege(PrivilegeEntity privilegeEntity) {
        if ( privilegeEntity == null ) {
            return null;
        }
        return privilegeMapper.map(privilegeEntity);
    }

    protected List<Privilege4Access> PrivilegeEntityListToPrivilegeList(List<PrivilegeEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<Privilege4Access> list1 = new ArrayList<Privilege4Access>( list.size() );
        for ( PrivilegeEntity privilegeEntity : list ) {
            list1.add( privilegeMapper.map(privilegeEntity));
        }

        return list1;
    }
}
