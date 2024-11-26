package id.beecolony.routeanalyze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{
    int removeMenuById(String id);
}
