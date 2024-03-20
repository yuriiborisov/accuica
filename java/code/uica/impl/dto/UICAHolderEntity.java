package code.uica.impl.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import code.uica.code.Config4UICA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "code.uica", schema = "public")
public class UICAHolderEntity {

	@Column(name = "form_id", unique = true, nullable = false)
	@Id
	private String formId;

	@Column(columnDefinition = "varchar(1000)")
	@Size(max = 1000)
	private String description;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "config", columnDefinition = "jsonb")
	private Config4UICA config;
}