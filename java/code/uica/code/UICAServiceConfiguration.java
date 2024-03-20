package code.uica.code;

import code.uica.code.service.EntityService4UICA;
import code.uica.code.service.HolderService4UICA;
import code.uica.code.service.PrivilegeService4UICA;

public interface UICAServiceConfiguration {
    JsEvaluator4UICA getJsEvaluator();
    EntityService4UICA resolveEntityService4UICAByEntityType(Object entity);
    PrivilegeService4UICA getPrivilegeService4UICA();
    HolderService4UICA getHolderService4UICA();
    UserService4UICA getUserService4UICA();


}
