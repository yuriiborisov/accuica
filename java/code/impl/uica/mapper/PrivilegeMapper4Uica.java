package code.impl.uica.mapper;

import code.accessor.core.code.Privilege4Access;
import code.impl.accessor.entity.PrivilegeEntity;
import code.uica.code.Privilege4UICA;
import org.springframework.stereotype.Component;


@Component
public class PrivilegeMapper4Uica {

    public Privilege4UICA map(PrivilegeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Privilege4UICA privilege = new Privilege4UICA();

        privilege.setId( entity.getId() );
        privilege.setCalculated( entity.isCalculated() );
        privilege.setStatementScript( entity.getStatementScript() );
        privilege.setUica( entity.isUica() );
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
