package id.beecolony.routeanalyze.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.auth.UserAuth;
import id.beecolony.routeanalyze.model.Role;
import id.beecolony.routeanalyze.model.User;
import id.beecolony.routeanalyze.model.dto.UserDto;
import id.beecolony.routeanalyze.repository.RoleRepository;
import id.beecolony.routeanalyze.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class UserService{

    @Autowired
    private Validator validator;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserAuth userAuth;

    public List<UserDto> getAll(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            Role role = user.getRole();

            userDto.setId(user.getId());
            userDto.setFullName(user.getFullName());
            userDto.setPassword(user.getPass());
            userDto.setPlaceOfBirth(user.getPlaceOfBirth());
            userDto.setDateOfBirth(user.getDateOfBirth());
            userDto.setAddress(user.getAddress());
            userDto.setEmail(user.getEmail());
            userDto.setRoleName(role != null?role.getRoleName():"uncategorize");
            userDto.setCreatedAt(Instant.ofEpochMilli(user.getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            userDto.setCreatedBy(user.getCreatedBy());

            userDtos.add(userDto);
        }

        return userDtos;
    }

    public UserDto getById(String id){
        Optional<User> userById = userRepository.findById(id);
        UserDto userDto = new UserDto();

        if(userById.isPresent()){
            User user = userById.get();
            userDto.setId(id);
            userDto.setFullName(user.getFullName());
            userDto.setPassword(user.getPass());
            userDto.setPlaceOfBirth(user.getPlaceOfBirth());
            userDto.setDateOfBirth(user.getDateOfBirth());
            userDto.setAddress(user.getAddress());
            userDto.setEmail(user.getEmail());
            userDto.setRoleName(user.getRole().getRoleName());
            userDto.setCreatedBy(user.getCreatedBy());
        }

        return userDto;
    }

    public User getByEmail(String email){

        Optional<User> byEmail = userRepository.getByEmail(email);

        return byEmail.isPresent()?byEmail.get():null;
    }

    public User getByUsernameAndPassword(String username, String password){

        Optional<User> byEmailAndPass = userRepository.getByEmailAndPass(username,password);

        return byEmailAndPass.isPresent()?byEmailAndPass.get():null;
    }

    public Map<String,String> save(UserDto userDto){

        Map<String,String> message = new HashMap<>();
        
        /* validasi */
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if(!violations.isEmpty()){
            for (ConstraintViolation<UserDto> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return message;
        }

        Optional<User> userByEmailOpt = userRepository.getByEmail(userDto.getEmail());
        if(userByEmailOpt.isPresent()){
            message.put("email", "Harap menggunakan email yang lain");
            return message;
        }


        /* */
        User user = null;
        String id = userDto.getId();
        
        Optional<Role> byId = roleRepository.findById(userDto.getRoleId());

        if(!id.isEmpty()){
            user = userRepository.findById(id).get();
            user.setUpdatedBy("System");
        }else{
            user = new User();
        }
        
        user.setFullName(userDto.getFullName());
        user.setPass(userDto.getPassword());
        user.setPlaceOfBirth(userDto.getPlaceOfBirth());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setRole(byId.isPresent() ? byId.get():null);

        User userSave = userRepository.save(user);

        if(userSave == null)
            message.put("error", "User tidak dapat disimpan");
        
        return message;
    }
    
    public boolean delete(String id){

        User userPrincipal = userAuth.userPrincipal();

        if(!userPrincipal.getId().equals(id)){
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
