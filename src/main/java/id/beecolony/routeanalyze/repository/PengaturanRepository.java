package id.beecolony.routeanalyze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.beecolony.routeanalyze.model.Pengaturan;

public interface PengaturanRepository extends JpaRepository<Pengaturan, String>{

    public List<Pengaturan> getAllByTipe(String tipe);
}
