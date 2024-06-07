package code.accessor.core.code.service;

import code.accessor.core.code.dto.PrivilegeEntityMatrix;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.impl.accessor.dto.request.Role4AccessRequest;
import code.impl.accessor.dto.request.Role4AccessRequestV2;

import java.util.List;

/**
 * General service to manage roles
 */
public interface RoleService4Access {
    /**
     * Get role by ID
     * @param id
     * @return
     */
    Role4AccessResponse getById(String id);

    /**
     * Update Role Matrix
     * @param requestV2
     * @return
     */
    Role4AccessResponse update(Role4AccessRequestV2 requestV2);

    /**
     * Update Role Info
     * @param request
     * @return
     */

    Role4AccessResponse updateInfo(Role4AccessRequest request);

    /**
     * Delet role from storage
     * @param id
     */
    void delete(String id);

    /**
     * Create new Role
     * @param request
     * @return
     */
    Role4AccessResponse create(Role4AccessRequest request);

    /**
     * Get all roles from storage
     * @return
     */
    List<Role4AccessResponse> getAll();

    /**
     * Get Role Matrix of grant accesses
     * @param id
     * @return
     */
    List<PrivilegeEntityMatrix> getMatrix(String id);

    /**
     * Get Parent Role Matrix of grant accesses (if exists)
     * @param id
     * @return
     */
    List<PrivilegeEntityMatrix> getMatrixOfParents(String id);

}
