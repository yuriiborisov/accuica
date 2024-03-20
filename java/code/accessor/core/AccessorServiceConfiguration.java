package code.accessor.core;

public interface AccessorServiceConfiguration {

    PrivilegeService4Access getPrivilegeService4Access();
    GrantAccessService4Access getGrantAccessService4Access();
//    SecurityService4Access getSecurityService4Access();
    UserService4Access getUserService4Access();

    RoleService4Access getRoleService4Access();

    FilterConfig4Access getFilterConfig4Access();

    MethodAccessService4Access getMethodAccessService4Access();

    JsEvaluator4Access getJsEvaluator();


}
