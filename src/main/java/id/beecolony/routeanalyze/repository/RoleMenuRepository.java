package id.beecolony.routeanalyze.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.beecolony.routeanalyze.model.RoleMenu;


public interface RoleMenuRepository extends JpaRepository<RoleMenu, String>{
    
    public Optional<RoleMenu> findFirstByRoleId(String roleId);

    public List<RoleMenu> findByRoleId(String roleId);
}
