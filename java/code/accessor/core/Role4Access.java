package code.accessor.core;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface Role4Access extends GrantedAuthority {
    String getId();
    List<GrantAccess4Access> getGrantAccesses();
    Role4Access getParent();
}
