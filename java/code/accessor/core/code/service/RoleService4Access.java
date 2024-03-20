package code.accessor.core.code.service;

import code.accessor.core.code.dto.PrivilegeEntityMatrix;
import code.accessor.core.code.dto.request.Role4AccessRequest;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.impl.dto.request.Role4AccessRequestV2;

import java.util.List;

public interface RoleService4Access {
    Role4AccessResponse getById(String id);
    Role4AccessResponse update(Role4AccessRequestV2 requestV2);
    void delete(String id);

    Role4AccessResponse create(Role4AccessRequest request);

    List<Role4AccessResponse> getAll();

    List<PrivilegeEntityMatrix> getMatrix(String id);
}
