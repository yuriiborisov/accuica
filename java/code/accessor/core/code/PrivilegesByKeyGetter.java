package code.accessor.core.code;

import java.util.List;

public interface PrivilegesByKeyGetter {
    List<Privilege4Access> getPrivileges(String key);
}
