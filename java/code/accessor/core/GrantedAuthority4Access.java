package code.accessor.core;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
@Getter
public class GrantedAuthority4Access implements GrantedAuthority {

    private final Role4Access role;

    public GrantedAuthority4Access(final Role4Access role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getId();
    }
}
