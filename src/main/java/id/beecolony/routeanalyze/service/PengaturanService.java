package id.beecolony.routeanalyze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import id.beecolony.routeanalyze.model.Pengaturan;
import id.beecolony.routeanalyze.repository.PengaturanRepository;
import id.beecolony.routeanalyze.utils.TaggingUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class PengaturanService {
    
    @Autowired
    private PengaturanRepository pengaturanRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private TaggingUtils taggingUtils;

    public List<Pengaturan> getByBcoTipe(){
        return pengaturanRepository.getAllByTipe("BCO");
    }

    @SuppressWarnings("unchecked")
    public List<Pengaturan> getByParamTipe(){
        List<Pengaturan> pengaturans = pengaturanRepository.getAllByTipe("PARAM");

        for (Pengaturan pengaturan : pengaturans) {
            
            if(pengaturan.getFormat().equals("JSON")){
                
                ObjectMapper ob = new ObjectMapper();

                try {
                    pengaturan.setTaggingValue(taggingUtils.selectedInput(pengaturan.getId(), ob.readValue(pengaturan.getLov(), HashMap.class),pengaturan.getValue(),Arrays.asList("form-select")));
                } catch (JsonProcessingException pe) {
                    pe.printStackTrace();
                }
            }
        }

        return pengaturans;
    }

    public Map<String,String> save(Pengaturan pengaturan){

        Map<String,String> message = new HashMap<>();
    
        Set<ConstraintViolation<Pengaturan>> violations = validator.validate(pengaturan);
    
        if(!violations.isEmpty()){
            for (ConstraintViolation<Pengaturan> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
    
            return message;
        }
    
        Pengaturan pengaturanNew = null;
        String id = pengaturan.getId();
    
        if(!id.isEmpty()){
            pengaturanNew = pengaturanRepository.findById(id).get();
            pengaturanNew.setUpdatedBy("System");
        }else{
            pengaturanNew = new Pengaturan();
        }
    
        pengaturanNew.setValue(pengaturan.getValue());
    
        Pengaturan pengaturanSave = pengaturanRepository.save(pengaturanNew);
    
        if(pengaturanSave == null)
            message.put("error", "Pengaturan tidak dapat disimpan");
    
        return message;
    }
    
}
