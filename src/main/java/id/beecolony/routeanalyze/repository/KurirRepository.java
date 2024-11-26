package id.beecolony.routeanalyze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.Kurir;

@Repository
public interface KurirRepository extends JpaRepository<Kurir, String>{
    
}
