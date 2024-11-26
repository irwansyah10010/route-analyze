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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.beecolony.routeanalyze.model.dto.RoleDto;
import id.beecolony.routeanalyze.model.dto.UserDto;
import id.beecolony.routeanalyze.service.RoleService;
import id.beecolony.routeanalyze.service.UserService;

@Controller
@RequestMapping(value = "user/")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "")
    public String index(Model model){
        model.addAttribute("users", userService.getAll());
        model.addAttribute("modaldelete", "view/template/modal-delete");
        model.addAttribute("content", "view/user/home");

        return "view/index";
    }

    
    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id){
        UserDto userById = userService.getById(id);

        List<RoleDto> all = roleService.getAll();

        model.addAttribute("roles", all);
        model.addAttribute("user", userById);
        model.addAttribute("content", "view/user/form");
        
        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes){
        
        Map<String,String> saves = userService.save(userDto);

        String url = "";

        if(saves.isEmpty()){
            model.addAttribute("content", "view/user/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/user/";
        }else{
            
            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("user", userDto);
            model.addAttribute("content", "view/user/form");

            String id = userDto.getId();

            if(id.isEmpty()){
                url = "redirect:/user/form/sa";
            }else{
                url = "redirect:/user/form/"+id;
            }
        }
        
        return url;
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model,  @PathVariable("id") String id, RedirectAttributes redirectAttributes){
        
        boolean delete = userService.delete(id);
        
        model.addAttribute("content", "view/user/home");

        if(!delete)
            redirectAttributes.addFlashAttribute("error", "Anda tidak bisa menghapus user tersebut");
        
        return "redirect:/user/";
    }
}
