package id.beecolony.routeanalyze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.PengambilanBarang;
import id.beecolony.routeanalyze.repository.PengambilanBarangRepository;

@Service
public class PengambilanBarangService {
    
    @Autowired
    private PengambilanBarangRepository pengambilanBarangRepository;


    public List<PengambilanBarang> getByLaporanId(String laporanId){
        return pengambilanBarangRepository.getAllByLaporanId(laporanId);
    }
}
