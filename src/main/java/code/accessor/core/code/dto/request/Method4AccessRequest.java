package code.accessor.core.code.dto.request;

import code.accessor.core.code.dto.Method4AccessBase;

import java.util.List;

public interface Method4AccessRequest extends Method4AccessBase {
    List<String> getPrivilegeIds();
}
