package code.impl.accessor.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantAccessRequestImpl {
    @Schema(name = "Role Id", description = "What Role the privileges would be set for", example = "ADMIN")
    private String roleId;

    @Schema(name = "Entity", description = "What Entity the privileges would be set for ", example = "NCR" )
    private String entityType;

    @Schema(name = "Privilege Ids",description = "Privileges for requested Entity & Role.", example = "[\"EDIT\"]" )
    private List<String> privilegeIds;
}
