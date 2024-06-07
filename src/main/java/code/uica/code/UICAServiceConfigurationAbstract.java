package code.uica.code;

import code.uica.code.nashorn.UicaNashornModule;
import code.uica.code.service.HolderService4UICA;
import code.uica.code.service.PrivilegeService4UICA;

public abstract class UICAServiceConfigurationAbstract implements UICAServiceConfiguration{


    @Override
    public JsEvaluator4UICA getJsEvaluator() {
        return new UicaNashornModule();
    }
}
