package code.accessor.impl.dto.request;

import code.accessor.core.code.dto.request.Role4AccessRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role4AccessRequestImpl implements Role4AccessRequest {

	@Schema(name ="Role id", description = "Role id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name ="Role name", description = "Name of Role.", example = "Power_admin" )
	private String name;

	@Schema(name ="Role description", description = "Description of Role.", example = "Power_admin role" )
	private String description;

	@Schema(name ="Priority", description = "Order list for Role." )
	private Integer sortOrder;

	@Schema(name ="Parent Id", description = "Parent Role id for current role.", example = "ADMIN" )
	private String parentId;
}
