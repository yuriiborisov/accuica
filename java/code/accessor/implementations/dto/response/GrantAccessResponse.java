package code.accessor.implementations.dto.response;

import code.accessor.core.GrantAccess4Access;
import code.accessor.core.Privilege4Access;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GrantAccessResponse implements GrantAccess4Access {
    @Schema(name = "Role Id", description = "Role that the privileges were set for", example = "ADMIN")
    private String roleId;

    @Schema(name = "Role Id", description = "Role that the privileges were set for", example = "NCR")
    private String entityType;

    @Schema(name = "Privilege", description = "Privileges for requested Entity & Role")
    private List<Privilege4Access> privileges;

}
