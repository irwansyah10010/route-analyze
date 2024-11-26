package id.beecolony.routeanalyze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.PengambilanBarang;

@Repository
public interface PengambilanBarangRepository extends JpaRepository<PengambilanBarang, String>{
    
    public List<PengambilanBarang> getAllByLaporanId(String laporanId);

    

    @Modifying
    @Query("DELETE FROM PengambilanBarang pb WHERE pb.laporan.id = :laporanId")
    public void removeAllByLaporanId(@Param("laporanId") String laporanId);
}
