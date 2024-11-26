package id.beecolony.routeanalyze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.PengaturanBco;

@Repository
public interface PengaturanBcoRepository extends JpaRepository<PengaturanBco, String>{
    
    public List<PengaturanBco> getAllByLaporanId(String laporanId);
}
