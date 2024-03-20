package code.accessor.implementations.dto.response;

import code.accessor.core.GrantAccess4Access;
import code.accessor.core.Role4Access;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleResponseLight {
	@Schema(name ="Role id", description = "Role with id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name ="Role name", description = "Name of Role.", example = "Power_admin" )
	private String name;

	@Schema(name ="Role description", description = "Description of Role.", example = "Power_admin role" )
	private String description;

	@Schema(name ="Parent Role", description = "Parent Role for current role.")
	private String parentId;

	@Schema(name ="Order for visual sorting", description = "Parent Role for current role.")
	private Integer sortOrder;

}
