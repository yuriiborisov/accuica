package code.impl.accessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

/**
 * Сущность для хранения дефолтных значений
 * Список привилегий для методов объекта системы
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="method_access",schema = "public")
public class MethodAccessEntity {
	@Id
	@Column(name="key", columnDefinition = "varchar(500)", nullable = false)
	private String key;

	@Column(name="method", columnDefinition = "varchar(200)")
	private String method;

	@Column(name="human_name", columnDefinition = "varchar(500)")
	private String humanName;

	@Column(name="description", columnDefinition = "varchar(1000)")
	private String description;

	@Column(name="entity_type", columnDefinition = "varchar(200)", nullable = false)
	private String entityType;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JsonIgnore
	private List<PrivilegeEntity> privileges;

	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<String> privilegeIds;

}
