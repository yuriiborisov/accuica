package code.accessor.core.impl;

import code.accessor.core.MethodAccess4Access;
import code.accessor.core.Privilege4Access;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccess4AccessImpl implements MethodAccess4Access {
	@Schema(name ="Entity", description = "Method set access for this code.entity", example = "NCR",requiredMode = REQUIRED )
	private String entityType;

	@Schema(name ="Key", description = "Method has key, defined in code by developer", example = "ncr_create",requiredMode = REQUIRED )
	private String key;

	@Schema(name ="Numan name", description = "The understandable name", example = "NCR_CREATION",requiredMode = REQUIRED)
	private String humanName;

	@Schema(name ="Description", description = "Description of method access.", example = "This method allows users to create NCR",requiredMode = REQUIRED )
	private String description;

	@Schema(name ="Method", description = "Deprecated. You can ignore it",requiredMode = NOT_REQUIRED )
	private String method;
	@Schema(name ="Privileges ids", description = "Privileges ids.")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<String> privilegeIds;

	@Schema(name ="Privileges", description = "Privileges list.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<Privilege4Access> privileges;
}
