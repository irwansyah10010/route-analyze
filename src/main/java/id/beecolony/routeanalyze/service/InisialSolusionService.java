package id.beecolony.routeanalyze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.InisialSolusion;
import id.beecolony.routeanalyze.repository.InisialSolusionRepository;

@Service
public class InisialSolusionService {
    

    @Autowired
    private InisialSolusionRepository inisialSolusionRepository;


    public List<InisialSolusion> getByLaporan(String laporanId){
        return inisialSolusionRepository.getAllByLaporanIdOrderByFitnessDesc(laporanId);
    }

}
