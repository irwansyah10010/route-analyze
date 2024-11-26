package id.beecolony.routeanalyze.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.Role;
import id.beecolony.routeanalyze.model.dto.RoleDto;
import id.beecolony.routeanalyze.repository.RoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class RoleService {

    @Autowired
    private Validator validator;
    
    @Autowired
    private RoleRepository roleRepository;

    public RoleDto getById(String id){
        Optional<Role> roleById = roleRepository.findById(id);
        RoleDto roleDto = new RoleDto();

        if(roleById.isPresent()){
            Role role = roleById.get();
            roleDto.setId(id);
            roleDto.setRoleCode(role.getRoleCode());
            roleDto.setRoleName(role.getRoleName());
            roleDto.setCreatedBy(role.getCreatedBy());
            // roleDto.setCreatedAt(role.getCreatedAt());
        }

        return roleDto;
    }

    public List<RoleDto> getAll(){
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();

            roleDto.setId(role.getId());
            roleDto.setRoleCode(role.getRoleCode());
            roleDto.setRoleName(role.getRoleName());
            roleDto.setCreatedAt(Instant.ofEpochMilli(role.getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            roleDto.setCreatedBy(role.getCreatedBy()); 

            roleDtos.add(roleDto);
        }

        return roleDtos;
    }

    public Map<String,String> save(RoleDto roleDto){

        Map<String,String> message = new HashMap<>();
        
        Set<ConstraintViolation<RoleDto>> violations = validator.validate(roleDto);

        if(!violations.isEmpty()){
            for (ConstraintViolation<RoleDto> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }

            return message;
        }

        Role role = null;

        String id = roleDto.getId();

        if(!id.isEmpty()){
            role = roleRepository.findById(id).get();
            role.setUpdatedBy("System");
        }else{
            role = new Role();
        }
        
        role.setRoleCode(roleDto.getRoleCode());
        role.setRoleName(roleDto.getRoleName());
        role.setCreatedBy("System");
        role.setIsActive(true);

        

        Role RoleSave = roleRepository.save(role);

        if(RoleSave == null)
            message.put("error", "Role tidak dapat disimpan");
        

        return message;
    }

    public boolean delete(String id){
        roleRepository.deleteById(id);

        return true;
    }
}
