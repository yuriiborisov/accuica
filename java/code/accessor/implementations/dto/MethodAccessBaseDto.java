package code.accessor.implementations.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccessBaseDto {
	@Schema(name ="Entity", description = "Method set access for this entity", example = "NCR",requiredMode = REQUIRED )
	private String entityType;

	@Schema(name ="Key", description = "Method has key, defined in code by developer", example = "ncr_create",requiredMode = REQUIRED )
	private String key;

	@Schema(name ="Numan name", description = "The understandable name", example = "NCR_CREATION",requiredMode = REQUIRED)
	private String humanName;

	@Schema(name ="Description", description = "Description of method access.", example = "This method allows users to create NCR",requiredMode = REQUIRED )
	private String description;

	@Schema(name ="Method", description = "Deprecated. You can ignore it",requiredMode = NOT_REQUIRED )
	private String method;
}
