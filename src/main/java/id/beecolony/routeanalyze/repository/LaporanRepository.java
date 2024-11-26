package id.beecolony.routeanalyze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.Laporan;

@Repository
public interface LaporanRepository extends JpaRepository<Laporan, String>{
    
}
