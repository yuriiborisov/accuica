package code.accessor.core;


import java.util.List;
import java.util.Set;

public interface MethodAccessService4Access {

	MethodAccess4Access getByKey(String id);

	List<MethodAccess4Access> getByKeys(Set<String> ids);

	MethodAccess4Access create(Object request);

//	List<Privilege4Access> getPrivileges(String key);

	MethodAccess4Access map(DefaultAccessInfo info);

}
