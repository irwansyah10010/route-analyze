package id.beecolony.routeanalyze.model;

import java.util.List;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
public class Role extends BaseMaster{

    @Column(name="role_code", length = 3,nullable = false)
	private String roleCode;
	
	@Column(name="role_name", length = 30,nullable = false)
	private String roleName;

    // @ManyToMany(mappedBy = "roles")
    // private List<Menu> menus;
    
}
