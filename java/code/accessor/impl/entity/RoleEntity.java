package code.accessor.impl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role",schema = "public")
public class RoleEntity {
	@Id
	@Column(unique = true, nullable = false, name = "id")
	private String id;

	@Column(name = "name", columnDefinition = "varchar(200)")
	private String name;

	@Column(columnDefinition = "varchar(500)")
	private String Description;

	@Column(name="sort_order")
	private Integer sortOrder;

	@Column(name="show_in_view")
	Boolean showInView;

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@Fetch(FetchMode.JOIN)
	private List<GrantAccessEntity> grantAccesses;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private RoleEntity parent;

	@Transient
	private String parentId;

	@Transient
	private List<String> grantAccessIds;

	public String getParentId(){
		return parent == null ? parentId : parent.getId();
	}

	@Transient
	@JsonIgnore
	public List<GrantAccessEntityPk> getGrantAccessIds(){
		return grantAccesses.stream().map(e->e.getId()).collect(Collectors.toList());
	}
}