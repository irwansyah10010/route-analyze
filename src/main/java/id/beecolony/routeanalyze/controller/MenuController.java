package id.beecolony.routeanalyze.controller;

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

import id.beecolony.routeanalyze.model.dto.MenuDto;
import id.beecolony.routeanalyze.service.MenuService;

@Controller
@RequestMapping(value = "menu/")
public class MenuController {

    @Autowired
    private MenuService menuService;
    
    @GetMapping(value = "")
    public String index(Model model){
        model.addAttribute("menus", menuService.getAll());
        model.addAttribute("content", "view/menu/home");
        
        return "view/index";
    }

    // @GetMapping(value = "form/")
    // public String form(Model model){
    //     model.addAttribute("menu", new MenuDto());
    //     model.addAttribute("content", "view/menu/form");
        
    //     return "view/index";
    // }

    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id){

        MenuDto menuById = menuService.getById(id);

        model.addAttribute("menu", menuById);
        model.addAttribute("content", "view/menu/form");
        
        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute MenuDto menuDto, RedirectAttributes redirectAttributes){
        
        Map<String,String> saves = menuService.save(menuDto);

        String url = "";

        if(saves.isEmpty()){
            model.addAttribute("content", "view/menu/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/menu/";
        }else{
            
            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("menu", menuDto);
            model.addAttribute("content", "view/menu/form");

            String id = menuDto.getId();

            if(id.isEmpty()){
                url = "redirect:/menu/form/sa";
            }else{
                url = "redirect:/menu/form/"+id;
            }
        }
        
        return url;
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model,  @PathVariable("id") String id){
        
        menuService.delete(id);

        model.addAttribute("content", "view/menu/home");
        
        return "redirect:/menu/";
    }

}
