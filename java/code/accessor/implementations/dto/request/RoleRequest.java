package code.accessor.implementations.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequest {

	@Schema(name ="Role id", description = "Role id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name ="Role name", description = "Name of Role.", example = "Power_admin" )
	private String name;

	@Schema(name ="Role description", description = "Description of Role.", example = "Power_admin role" )
	private String description;

	@Schema(name ="Priority", description = "Order list for Role." )
	private Integer priority;

	@Schema(name ="Parent Id", description = "Parent Role id for current role.", example = "ADMIN" )
	private String parentId;
}
