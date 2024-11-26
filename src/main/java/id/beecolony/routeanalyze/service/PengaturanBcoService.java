package id.beecolony.routeanalyze.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.Laporan;
import id.beecolony.routeanalyze.model.PengaturanBco;
import id.beecolony.routeanalyze.repository.LaporanRepository;
import id.beecolony.routeanalyze.repository.PengaturanBcoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class PengaturanBcoService {
    
    @Autowired
    private PengaturanBcoRepository pengaturanBcoRepository;

    @Autowired
    private LaporanRepository laporanRepository;

    @Autowired
    private Validator validator;

    public PengaturanBco getById(String id){
        Optional<PengaturanBco> byId = pengaturanBcoRepository.findById(id);
        
        if(byId.isPresent()){
            return byId.get();
        }

        return new PengaturanBco();
    }

    public List<PengaturanBco> getAllByLaporanId(String laporanId){
        return pengaturanBcoRepository.getAllByLaporanId(laporanId);
    }

    public Map<String,String> save(PengaturanBco pengaturanBco){

        Map<String,String> message = new HashMap<>();
        
        
        Set<ConstraintViolation<PengaturanBco>> violations = validator.validate(pengaturanBco);

        if(!violations.isEmpty()){
            for (ConstraintViolation<PengaturanBco> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return message;
        }

        PengaturanBco pengaturanBcoNew = null;
        String id = pengaturanBco.getId();

        if(!id.isEmpty()){
            pengaturanBcoNew = pengaturanBcoRepository.findById(id).get();
            pengaturanBcoNew.setUpdatedBy("System");
        }else{
            pengaturanBcoNew = new PengaturanBco();
        }

        String error="";
        if(pengaturanBco.getPopulationSize() < pengaturanBco.getMaxIterations()){
            error = "Max Iteration tidak boleh lebih dari populasi";
        }else if(pengaturanBco.getPopulationSize() < pengaturanBco.getEmployedBees()){
            error = "Employee Bess tidak boleh lebih dari populasi";
        }else if(pengaturanBco.getPopulationSize() < pengaturanBco.getOnlookerBees()){
            error = "On Looker Bees tidak boleh lebih dari populasi";
        }else if(pengaturanBco.getPopulationSize() < pengaturanBco.getScoutBees()){
            error = "Scout Bees tidak boleh lebih dari populasi";
        }
            
        if(error.isEmpty()){
            pengaturanBcoNew.setMaxIterations(pengaturanBco.getMaxIterations());
            pengaturanBcoNew.setEmployedBees(pengaturanBco.getEmployedBees());
            pengaturanBcoNew.setOnlookerBees(pengaturanBco.getOnlookerBees());
            pengaturanBcoNew.setScoutBees(pengaturanBco.getScoutBees());
            pengaturanBcoNew.setObjective(pengaturanBco.getObjective());
            
            Laporan laporan = laporanRepository.findById(pengaturanBco.getLaporan().getId()).get();
            pengaturanBcoNew.setLaporan(laporan);
    
            PengaturanBco pengaturanBcoSave = pengaturanBcoRepository.save(pengaturanBcoNew);
    
            if(pengaturanBcoSave == null)
                message.put("error", "Pengaturan BCO tidak dapat disimpan");
        }
        
        return message;
    }

}
