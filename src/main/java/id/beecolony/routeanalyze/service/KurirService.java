package id.beecolony.routeanalyze.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.Kurir;
import id.beecolony.routeanalyze.repository.KurirRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class KurirService {
    
    @Autowired
    private KurirRepository kurirRepository;

    @Autowired
    private Validator validator;

    public List<Kurir> getAll(){
        return kurirRepository.findAll();
    }

    public Kurir getById(String id){
        Optional<Kurir> kurirById = kurirRepository.findById(id);
        

        if(kurirById.isPresent()){
            return kurirById.get();
        }

        return new Kurir();
    }

    public Map<String,String> save(Kurir kurir){

        Map<String,String> message = new HashMap<>();
        
        Set<ConstraintViolation<Kurir>> violations = validator.validate(kurir);

        if(!violations.isEmpty()){
            for (ConstraintViolation<Kurir> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }

            return message;
        }

        Kurir kurirNew = null;
        String id = kurir.getId();

        if(!id.isEmpty()){
            kurirNew = kurirRepository.findById(id).get();
            kurirNew.setUpdatedBy("System");
        }else{
            kurirNew = new Kurir();
            kurirNew.setCreatedAt(kurir.getCreatedAt());
            kurirNew.setCreatedBy(kurir.getCreatedBy());
        }

        kurirNew.setNama(kurir.getNama());
        kurirNew.setNomorTelepon(kurir.getNomorTelepon());

        Kurir kurirSave = kurirRepository.save(kurirNew);

        if(kurirSave == null)
            message.put("error", "Kurir tidak dapat disimpan");
        
        return message;
    }

    public boolean delete(String id){
        kurirRepository.deleteById(id);

        return true;
    }
}
