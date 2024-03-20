package code.accessor.implementations.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class RoleRequestV2 {

	@Schema(name ="Role id", description = "Role id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name = "Privilege Ids",description = "Privileges for requested Entity & Role.", example = "[\"EDIT\"]" )
	private List<GrantAccess> grantAccesses;


}
