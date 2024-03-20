package code.accessor.core;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
@Getter
public class GrantedAuthority4Access implements GrantedAuthority {

    private final Role4AccessResponse role;

    public GrantedAuthority4Access(final Role4AccessResponse role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getId();
    }
}
