package code.accessor.core.code;

import code.accessor.core.code.service.GrantAccessService4Access;
import code.accessor.core.code.service.MethodAccessService4Access;
import code.accessor.core.code.service.PrivilegeService4Access;
import code.accessor.core.code.service.RoleService4Access;
import code.accessor.core.code.service.UserService4Access;

public interface AccessorServiceConfiguration {

    PrivilegeService4Access getPrivilegeService4Access();
    GrantAccessService4Access getGrantAccessService4Access();
    UserService4Access getUserService4Access();

    RoleService4Access getRoleService4Access();

    FilterConfig4Access getFilterConfig4Access();

    MethodAccessService4Access getMethodAccessService4Access();

    JsEvaluator4Access getJsEvaluator();

}
