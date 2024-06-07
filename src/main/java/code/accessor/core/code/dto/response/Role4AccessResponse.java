package code.accessor.core.code.dto.response;

import code.accessor.core.code.dto.GrantAccess4Access;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface Role4AccessResponse extends GrantedAuthority {
    String getId();
    String getName();
    String getDescription();
    Integer getSortOrder();
    List<GrantAccess4Access> getGrantAccesses();
    Role4AccessResponse getParent();

}
