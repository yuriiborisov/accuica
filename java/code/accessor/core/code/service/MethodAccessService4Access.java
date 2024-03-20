package code.accessor.core.code.service;


import code.accessor.core.code.dto.response.Method4AccessResponse;

import java.util.List;
import java.util.Set;

public interface MethodAccessService4Access {

	Method4AccessResponse getByKey(String id);

	List<Method4AccessResponse> getByKeys(Set<String> ids);

	Method4AccessResponse create(Object request);


}
