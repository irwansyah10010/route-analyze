package id.beecolony.routeanalyze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
    
}
