package code.impl.uica.entity;

import code.uica.code.Config4UICA;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.Size;

@Schema(description = "UICA - Uni-graphic Interface Component Access")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uica", schema = "public")
public class UICAHolder {

	@Id
	@Column(name = "form_id",unique = true, columnDefinition = "varchar(255)", nullable = false )
	private String formId;

	@Column(columnDefinition = "varchar(1000)")
	@Size(max = 1000)
	private String description;

	@Type(type = "json")
	@Column(name="config")
	private Config4UICA config;

}