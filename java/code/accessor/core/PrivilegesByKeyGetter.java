package code.accessor.core;

import java.util.List;

public interface PrivilegesByKeyGetter {
    List<Privilege4Access> getPrivileges(String key);
}
