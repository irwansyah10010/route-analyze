package id.beecolony.routeanalyze.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.Pemasok;
import id.beecolony.routeanalyze.repository.PemasokRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;



@Service
public class PemasokService {

    @Autowired
    private PemasokRepository pemasokRepository;

    @Autowired
    private Validator validator;

    public List<Pemasok> getAll() {
        return pemasokRepository.findAll();
    }

    public Pemasok getById(String id) {
        Optional<Pemasok> pemasokById = pemasokRepository.findById(id);

        if (pemasokById.isPresent()) {
            return pemasokById.get();
        }

        return new Pemasok();
    }

    public Map<String, String> save(Pemasok pemasok) {

        Map<String, String> message = new HashMap<>();

        Set<ConstraintViolation<Pemasok>> violations = validator.validate(pemasok);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Pemasok> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }

            return message;
        }

        Pemasok pemasokNew = null;
        String id = pemasok.getId();

        if (!id.isEmpty()) {
            pemasokNew = pemasokRepository.findById(id).get();
            pemasokNew.setUpdatedBy("System");
        } else {
            pemasokNew = new Pemasok();
        }

        pemasokNew.setNamaPemasok(pemasok.getNamaPemasok());
        pemasokNew.setAlamat(pemasok.getAlamat());
        pemasokNew.setLatitude(pemasok.getLatitude());
        pemasokNew.setLongitude(pemasok.getLongitude());
        pemasokNew.setCreatedAt(pemasok.getCreatedAt());
        pemasokNew.setCreatedBy(pemasok.getCreatedBy());

        Pemasok pemasokSave = pemasokRepository.save(pemasokNew);

        if (pemasokSave == null)
            message.put("error", "Pemasok tidak dapat disimpan");

        return message;
    }

    public boolean delete(String id) {
        pemasokRepository.deleteById(id);
        return true;
    }
}

