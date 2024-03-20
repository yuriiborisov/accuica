package code.accessor.impl.mapper;

import code.accessor.core.code.Privilege4Access;
import code.accessor.impl.dto.Privilege4AccessImpl;
import code.accessor.impl.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;


@Component
public class PrivilegeMapperImpl {

    public Privilege4Access map(PrivilegeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Privilege4AccessImpl privilege = new Privilege4AccessImpl();

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

    public PrivilegeEntity map(Privilege4Access privilege) {
        if ( privilege == null ) {
            return null;
        }

        PrivilegeEntity PrivilegeEntity = new PrivilegeEntity();

        PrivilegeEntity.setId( privilege.getId() );
        PrivilegeEntity.setAlt( privilege.getAlt() );
        PrivilegeEntity.setDescription( privilege.getDescription() );
        PrivilegeEntity.setPriorityOrder( privilege.getPriorityOrder() );
        if ( privilege.getCalculated() != null ) {
            PrivilegeEntity.setCalculated( privilege.getCalculated() );
        }
        PrivilegeEntity.setStatementScript( privilege.getStatementScript() );
        PrivilegeEntity.setFilterValue( privilege.getFilterValue() );
        PrivilegeEntity.setUica( privilege.getUica() );
        PrivilegeEntity.setFilter( privilege.isFilter() );

        return PrivilegeEntity;
    }
}
