package code.impl.uica.mapper;

import code.accessor.core.code.Privilege4Access;
import code.impl.accessor.entity.PrivilegeEntity;
import code.impl.uica.entity.UICAHolder;
import code.uica.code.Holder4UICA;
import code.uica.code.Privilege4UICA;
import org.springframework.stereotype.Component;


@Component
public class HolderMapper4Uica {

    public Holder4UICA map(UICAHolder entity) {
        if ( entity == null ) {
            return null;
        }

        Holder4UICA holder4UICA = new Holder4UICA();

        holder4UICA.setFormId(entity.getFormId());
        holder4UICA.setDescription(entity.getDescription());
        holder4UICA.setConfig(entity.getConfig());

        return holder4UICA;
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
