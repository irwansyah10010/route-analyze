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

import id.beecolony.routeanalyze.model.Menu;
import id.beecolony.routeanalyze.model.dto.MenuDto;
import id.beecolony.routeanalyze.repository.MenuRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class MenuService {

    @Autowired
    private Validator validator;
    
    @Autowired
    private MenuRepository menuRepository;

    public MenuDto getById(String id){
        Optional<Menu> menuById = menuRepository.findById(id);
        MenuDto menuDto = new MenuDto();

        if(menuById.isPresent()){
            Menu menu = menuById.get();
            menuDto.setId(id);
            menuDto.setName(menu.getName());
            menuDto.setModule(menu.getModule());
            menuDto.setParentId(menu.getParentId());
        }

        return menuDto;
    }

    public List<MenuDto> getAll(){
        List<Menu> menus = menuRepository.findAll();
        List<MenuDto> menuDtos = new ArrayList<>();

        for (Menu menu : menus) {
            MenuDto menuDto = new MenuDto();

            menuDto.setId(menu.getId());
            menuDto.setName(menu.getName());
            menuDto.setModule(menu.getModule());
            menuDto.setParentId(menu.getParentId());
            
            menuDto.setCreatedAt(Instant.ofEpochMilli(menu.getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            menuDto.setCreatedBy(menu.getCreatedBy());

            menuDtos.add(menuDto);
        }

        return menuDtos;
    }

    public Map<String,String> save(MenuDto menuDto){

        Map<String,String> message = new HashMap<>();
        
        Set<ConstraintViolation<MenuDto>> violations = validator.validate(menuDto);

        if(!violations.isEmpty()){
            for (ConstraintViolation<MenuDto> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }

            return message;
        }

        Menu menu = null;
        String id = menuDto.getId();

        if(!id.isEmpty()){
            menu = menuRepository.findById(id).get();
            menu.setUpdatedBy("System");
        }else{
            menu = new Menu();
        }

        menu.setName(menuDto.getName());
        menu.setModule(menuDto.getModule());
        menu.setParentId(menuDto.getParentId());

        Menu menuSave = menuRepository.save(menu);

        if(menuSave == null)
            message.put("error", "Menu tidak dapat disimpan");
        
        return message;
    }

    public boolean delete(String id){
        menuRepository.deleteById(id);

        return true;
    }
}
