package id.beecolony.routeanalyze.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

import id.beecolony.routeanalyze.model.Menu;
import id.beecolony.routeanalyze.model.dto.RoleDto;
import id.beecolony.routeanalyze.service.RoleMenuService;
import id.beecolony.routeanalyze.service.RoleService;

@Controller
@RequestMapping(value = "role/")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @GetMapping(value = "")
    public String index(Model model){
        
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("content", "view/role/home");
        
        return "view/index";
    }

    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id){
        RoleDto roleById = roleService.getById(id);

        model.addAttribute("role", roleById);
        model.addAttribute("content", "view/role/form");
        
        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute RoleDto roleDto, RedirectAttributes redirectAttributes){
        
        Map<String,String> saves = roleService.save(roleDto);

        String url = "";

        if(saves.isEmpty()){
            model.addAttribute("content", "view/role/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/menu/";
        }else{

            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("role", roleDto);
            model.addAttribute("content", "view/role/form");

            String id = roleDto.getId();

            if(id.isEmpty()){
                url = "redirect:/role/form/sa";
            }else{
                url = "redirect:/role/form/"+id;
            }
        }
        
        return url;
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model,  @PathVariable("id") String id){
        
        roleService.delete(id);

        model.addAttribute("content", "view/role/home");
        
        return "redirect:/role/";
    }
}
