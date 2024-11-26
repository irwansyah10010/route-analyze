package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseRelation;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "role_menu")
@Data
public class RoleMenu extends BaseRelation{
    
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, columnDefinition = "varchar(36)")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, columnDefinition = "varchar(36)")
    private Role role;
    
}
