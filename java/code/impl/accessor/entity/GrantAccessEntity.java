package code.impl.accessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="grant_access",schema = "public")
public class GrantAccessEntity {

	@EmbeddedId
	@JsonIgnore
	private GrantAccessEntityPk id;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH })
	@JsonIgnore
	private List<PrivilegeEntity> privileges;

	@Transient
	private List<String> privilegeIds;

	public List<String> getPrivilegeIds(){
		return privileges == null ? privilegeIds : privileges.stream().map(e->e.getId()).collect(Collectors.toList());
	}

	public String getRoleId(){
		return id.getRoleId();
	}

	public String getEntityType(){
		return id.getEntityType();
	}

}
