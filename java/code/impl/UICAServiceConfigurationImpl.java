package code.impl;

import code.impl.uica.service.HolderService4UICAImpl;
import code.uica.code.*;
import code.uica.code.service.HolderService4UICA;
import code.uica.code.service.PrivilegeService4UICA;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UICAServiceConfigurationImpl extends UICAServiceConfigurationAbstract {

    private final PrivilegeService4UICA privilegeService4UICA;
    private final HolderService4UICAImpl holderService4UICA;



    public UICAServiceConfigurationImpl(PrivilegeService4UICA privilegeService4UICA, HolderService4UICAImpl holderService4UICA) {
        this.privilegeService4UICA = privilegeService4UICA;
        this.holderService4UICA = holderService4UICA;
    }

    @Override
    public EntityUICAFactory getEntityUICAFactory() {
        return null;
    }

    @Override
    public PrivilegeService4UICA getPrivilegeService4UICA() {
        return privilegeService4UICA;
    }

    @Override
    public HolderService4UICA getHolderService4UICA() {
        return holderService4UICA;
    }

    @Override
    public UserService4UICA getUserService4UICA() {
        return null;
    }
}
