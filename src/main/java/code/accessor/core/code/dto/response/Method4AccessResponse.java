package code.accessor.core.code.dto.response;

import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.dto.Method4AccessBase;

import java.util.List;

public interface Method4AccessResponse extends Method4AccessBase {
    List<Privilege4Access> getPrivileges();
}
