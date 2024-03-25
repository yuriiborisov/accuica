package code.accessor.core.code.service;


import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.dto.request.Method4AccessRequest;
import code.accessor.core.code.dto.response.Method4AccessResponse;
import code.accessor.core.update.Entity4AccessConfig;
import code.accessor.core.update.Method4AccessConfig;

import java.util.List;
import java.util.Set;

public interface MethodAccessService4Access {

	Method4AccessResponse getByKey(String id);

	List<Method4AccessResponse> getByKeys(Set<String> ids);

	List<Privilege4Access> getPrivileges(String key);

	List<Method4AccessResponse> getAll();

	List<Method4AccessConfig> getMethods();
	List<Entity4AccessConfig> getMethodsByEntity(String entity);
	Method4AccessResponse create(Method4AccessRequest request);


}
