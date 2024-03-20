package code.accessor.implementations.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GrantAccessEntityPk implements Serializable {
	@Column(name="role_id", columnDefinition = "varchar(200)")
	private String roleId;

	@Column(name="entity_type", columnDefinition = "varchar(200)")
	@JsonIgnore
	private String entityType;
}
