package id.beecolony.routeanalyze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.InisialSolusion;

@Repository
public interface InisialSolusionRepository extends JpaRepository<InisialSolusion, String>{
    
    public List<InisialSolusion> getAllByLaporanIdOrderByFitnessDesc(String laporanId);
}
