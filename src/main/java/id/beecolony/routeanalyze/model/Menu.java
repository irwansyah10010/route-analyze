package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Menu extends BaseMaster{
    
    @Column(name = "menu_name", length = 50, nullable = false)
    private String name;

    @Column(name = "module", length = 75, nullable = false)
    private String module;

    @Column(name = "parent_id", length = 32 , nullable = true)
    private String parentId;
    
    @Column(name = "menu_url", length = 50 , nullable = true)
    private String menuUrl;

    // @ManyToMany
    // @JoinTable(name = "role_menu", 
    // joinColumns = @JoinColumn(name="menu_id"),
    // inverseJoinColumns = @JoinColumn(name="role_id"))
    // private List<Role> roles; 
}
