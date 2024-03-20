package code.accessor.implementations.mapper;

import code.accessor.core.Privilege4Access;
import code.accessor.implementations.dto.response.PrivilegeResponse;
import code.accessor.implementations.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;


@Component
public class PrivilegeMapperImpl implements PrivilegeMapper {

    @Override
    public Privilege4Access map(PrivilegeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PrivilegeResponse privilege = new PrivilegeResponse();

        privilege.setId( entity.getId() );
        privilege.setAlt( entity.getAlt() );
        privilege.setDescription( entity.getDescription() );
        privilege.setPriorityOrder( entity.getPriorityOrder() );
        privilege.setCalculated( entity.isCalculated() );
        privilege.setStatementScript( entity.getStatementScript() );
        privilege.setFilterValue( entity.getFilterValue() );
        privilege.setUica( entity.isUica() );
        privilege.setFilter( entity.isFilter() );
        privilege.setApplicable( entity.getApplicable() );

        return privilege;
    }

//    @Override
//    public PrivilegeEntity map(Privilege privilege) {
//        if ( privilege == null ) {
//            return null;
//        }
//
//        PrivilegeEntity PrivilegeEntity = new PrivilegeEntity();
//
//        PrivilegeEntity.setId( privilege.getId() );
//        PrivilegeEntity.setAlt( privilege.getAlt() );
//        PrivilegeEntity.setDescription( privilege.getDescription() );
//        PrivilegeEntity.setPriorityOrder( privilege.getPriorityOrder() );
//        if ( privilege.getCalculated() != null ) {
//            PrivilegeEntity.setCalculated( privilege.getCalculated() );
//        }
//        PrivilegeEntity.setStatementScript( privilege.getStatementScript() );
//        PrivilegeEntity.setFilterValue( privilege.getFilterValue() );
//        PrivilegeEntity.setUica( privilege.isUica() );
//        PrivilegeEntity.setFilter( privilege.isFilter() );
//
//        return PrivilegeEntity;
//    }
}
