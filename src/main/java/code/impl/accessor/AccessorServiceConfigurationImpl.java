package code.impl.accessor;

import code.accessor.core.code.*;
import code.accessor.core.code.service.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessorServiceConfigurationImpl implements AccessorServiceConfiguration {
    private final PrivilegeService4Access privilegeService4Access;
    private final GrantAccessService4Access grantAccessService4Access;
    private final UserService4Access userService4Access;
    private final RoleService4Access roleService4Access;
    private final FilterConfig4Access filterConfig4Access;
    private final MethodAccessService4Access methodAccessService4Access;
    private final JsEvaluator4Access jsEvaluator4Access;

    public AccessorServiceConfigurationImpl(PrivilegeService4Access privilegeService4Access,
                                            GrantAccessService4Access grantAccessService4Access,
                                            UserService4Access userService4Access,
                                            RoleService4Access roleService4Access,
//                                            FilterConfig4Access filterConfig4Access,
                                            MethodAccessService4Access methodAccessService4Access,
                                            JsEvaluator4Access jsEvaluator4Access, AccessorCalculator calculator) {
        this.privilegeService4Access = privilegeService4Access;
        this.grantAccessService4Access = grantAccessService4Access;
        this.userService4Access = userService4Access;
        this.roleService4Access = roleService4Access;
        this.filterConfig4Access = (o) -> null;
        this.methodAccessService4Access = methodAccessService4Access;
        this.jsEvaluator4Access = jsEvaluator4Access;
    }

    @Override
    public PrivilegeService4Access getPrivilegeService4Access() {
        return privilegeService4Access;
    }

    @Override
    public GrantAccessService4Access getGrantAccessService4Access() {
        return grantAccessService4Access;
    }

    @Override
    public UserService4Access getUserService4Access() {
        return userService4Access;
    }

    @Override
    public RoleService4Access getRoleService4Access() {
        return roleService4Access;
    }

    @Override
    public FilterConfig4Access getFilterConfig4Access() {
        return filterConfig4Access;
    }

    @Override
    public MethodAccessService4Access getMethodAccessService4Access() {
        return methodAccessService4Access;
    }

    @Override
    public JsEvaluator4Access getJsEvaluator() {
        return jsEvaluator4Access;
    }

}
