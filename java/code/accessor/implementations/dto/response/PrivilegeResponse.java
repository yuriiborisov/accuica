package code.accessor.implementations.dto.response;

import code.accessor.core.Privilege4Access;
import code.accessor.implementations.dto.PrivilegeApplicable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PrivilegeResponse implements Privilege4Access {
	@Schema(description = "id for Privilege. Required for edit. Ignored for new record", example = "EDIT")
	private String id;

	@Schema(description = "alt for Privilege. Alternative name of privilege", example = "Edit_Entity")
	private String alt;

	@Schema(description = "Description of Privilege.", example = "Allow edit entity")
	private String description;

	@Schema(description = "Priority order of Privilege. The importance among other privileges", example = "120")
	private Integer priorityOrder;

	@Schema(description = "Calculated flag for Privilege. Is privileges calculated or not", example = "true")
	private boolean calculated;

	@Schema(description = "Statement script for Privilege. Script for calculated privilege only", example = "'REGISTERED'.equals($.getNcr(entity.getId()).getStatus())")
	private String statementScript;

	@Schema(description = "Statement script for Privilege. Script for calculated privilege only", example = "'REGISTERED'.equals($.getNcr(entity.getId()).getStatus())")
	private Object filterValue;

	@Schema(description = "Is privilege code.uica or not", example = "false")
	private boolean uica;

	@Schema(description = "Is privilege filter or not", example = "false")
	private boolean filter;

	private PrivilegeApplicable applicable;

}