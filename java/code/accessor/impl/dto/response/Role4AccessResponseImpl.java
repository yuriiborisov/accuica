package code.accessor.impl.dto.response;

import code.accessor.core.code.dto.GrantAccess4Access;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role4AccessResponseImpl implements Role4AccessResponse {
	@Schema(name ="Role id", description = "Role with id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name ="Role name", description = "Name of Role.", example = "Power_admin" )
	private String name;

	@Schema(name ="Role description", description = "Description of Role.", example = "Power_admin role" )
	private String description;

	@Schema(name ="Grant Accesses", description = "Grant access list for Role.")
	private List<GrantAccess4Access> grantAccesses;

	@Schema(name ="Parent Role", description = "Parent Role for current role.")
	private Role4AccessResponse parent;

	@Schema(name ="Priority for visual sorting", description = "Parent Role for current role.")
	private Integer sortOrder;

	@Override
	public String getAuthority() {
		return id;
	}

}
