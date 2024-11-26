package id.beecolony.routeanalyze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.Pemasok;

@Repository
public interface PemasokRepository extends JpaRepository<Pemasok, String>{
    
}
