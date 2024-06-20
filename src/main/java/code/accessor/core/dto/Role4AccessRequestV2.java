package code.accessor.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class Role4AccessRequestV2 {

	@Schema(name ="Role id", description = "Role id.", example = "POWER_ADMIN" )
	private String id;

	@Schema(name = "Privilege Ids",description = "Privileges for requested Entity & Role.", example = "[\"EDIT\"]" )
	private List<GrantAccessRequestImpl> grantAccesses;


}
