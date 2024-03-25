package code.impl.accessor.mapper;

import code.accessor.core.code.DefaultAccessInfo;
import code.accessor.core.code.dto.request.Method4AccessRequest;
import code.accessor.core.code.dto.response.Method4AccessResponse;
import code.accessor.core.update.Method4AccessConfig;
import code.accessor.core.update.Privilege4AccessConfig;
import code.impl.accessor.dto.response.MethodAccess4AccessImpl;
import code.accessor.core.code.Privilege4Access;
import code.impl.accessor.entity.MethodAccessEntity;
import code.impl.accessor.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MethodAccessMapperImpl {

    private final PrivilegeMapperImpl privilegeMapper;

    public MethodAccessMapperImpl(PrivilegeMapperImpl privilegeMapper) {
        this.privilegeMapper = privilegeMapper;
    }

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


    public MethodAccessEntity map(Method4AccessRequest request) {
        return null;
    }


    public Method4AccessResponse map(DefaultAccessInfo info) {
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


    public Method4AccessConfig map_(MethodAccessEntity entity){
        Method4AccessConfig method4AccessConfig = new Method4AccessConfig();
        method4AccessConfig.setMethodId(entity.getKey());
        method4AccessConfig.setPrivileges(entity.getPrivileges().stream().map(p -> new Privilege4AccessConfig(p.getId())).collect(Collectors.toList()));
        return method4AccessConfig;
    }

//    public MethodAccessEntity map_(Method4AccessConfig config){
//        MethodAccessEntity entity = new MethodAccessEntity();
//        entity.setMethod();
//        entity.setEntityType();
//        entity.setKey();
//        entity.setPrivilegeIds();
//        method4AccessConfig.setMethodId(entity.getKey());
//        method4AccessConfig.setPrivileges(entity.getPrivileges().stream().map(p -> new Privilege4AccessConfig(p.getId())).collect(Collectors.toList()));
//        return method4AccessConfig;
//    }
}
