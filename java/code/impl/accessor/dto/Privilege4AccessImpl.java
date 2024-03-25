package code.impl.accessor.dto;

import code.accessor.core.code.Privilege4Access;
import code.common.PrivilegeApplicable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Privilege4AccessImpl implements Privilege4Access {
	@Schema(description = "id for Privilege. Required for edit. Ignored for new record", example = "EDIT")
	private String id;

	@Schema(description = "alt for Privilege. Alternative name of privilege", example = "Edit_Entity")
	private String alt;

	@Schema(description = "Description of Privilege.", example = "Allow edit entity")
	private String description;

	@Schema(description = "Priority order of Privilege. The importance among other privileges", example = "120")
	private Integer priorityOrder;

	@Schema(description = "Calculated flag for Privilege. Is privileges calculated or not", example = "true")
	private Boolean calculated;

	@Schema(description = "Statement script for Privilege. Script for calculated privilege only", example = "'REGISTERED'.equals($.getNcr(entity.getId()).getStatus())")
	private String statementScript;

	@Schema(description = "Statement script for Privilege. Script for calculated privilege only", example = "'REGISTERED'.equals($.getNcr(entity.getId()).getStatus())")
	private Object filterValue;

	@Schema(description = "Is privilege code.uica or not", example = "false")
	private Boolean uica;

	@Schema(description = "Is privilege filter or not", example = "false")
	private boolean filter;

	private PrivilegeApplicable applicable;

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof Privilege4AccessImpl)) return false;
		final Privilege4AccessImpl other = (Privilege4AccessImpl) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.getId();
		final Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Privilege4AccessImpl;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.getId();
		result = result * PRIME + ($id == null ? 43 : $id.hashCode());
		return result;
	}
}
