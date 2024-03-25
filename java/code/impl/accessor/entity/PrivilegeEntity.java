package code.impl.accessor.entity;


import code.common.PrivilegeApplicable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.type.SqlTypes;

/**
 * Сущность привилегии
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "privilege", schema = "public")
public class PrivilegeEntity {

	@Id
	@Column(name = "id", unique = true, columnDefinition = "varchar(200)")
	private String id;

	@Column(name = "alt", columnDefinition = "varchar(200)")
	private String alt;

	@Column(name = "description", columnDefinition = "varchar")
	private String description;

	@Column(name = "priority_order", columnDefinition = "int")
	private Integer priorityOrder;

	@Column(name = "is_calculated", columnDefinition = "boolean default false")
	private boolean calculated;

	@Column(name = "statement_script", columnDefinition = "varchar")
	private String statementScript;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name="filter_value", columnDefinition = "jsonb")
	private Object filterValue;

	@Column(name = "is_uica", columnDefinition = "boolean default false")
	private boolean uica;

	@Column(name = "is_filter", columnDefinition = "boolean default false")
	private boolean filter;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name="applicable", columnDefinition = "jsonb")
	private PrivilegeApplicable applicable;
}
