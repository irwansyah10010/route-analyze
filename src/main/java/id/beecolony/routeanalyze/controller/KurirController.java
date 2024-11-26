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

import id.beecolony.routeanalyze.model.Kurir;
import id.beecolony.routeanalyze.service.KurirService;

@Controller
@RequestMapping(value = "kurir/")
public class KurirController {
    
    @Autowired
    private KurirService kurirService;

    @GetMapping(value = "")
    public String index(Model model){
        model.addAttribute("kurirs", kurirService.getAll());
        model.addAttribute("modaldelete", "view/template/modal-delete");
        model.addAttribute("content", "view/kurir/home");

        return "view/index";
    }

    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id){

        Kurir kurirById = kurirService.getById(id);

        model.addAttribute("kurir", kurirById);
        model.addAttribute("content", "view/kurir/form");
        
        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute Kurir kurir, RedirectAttributes redirectAttributes){
        
        Map<String,String> saves = kurirService.save(kurir);

        String url = "";

        if(saves.isEmpty()){

            model.addAttribute("content", "view/kurir/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/kurir/";
        }else{
            
            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("kurir", kurir);
            model.addAttribute("content", "view/kurir/form");

            String id = kurir.getId();

            if(id.isEmpty()){
                url = "redirect:/kurir/form/sa";
            }else{
                url = "redirect:/kurir/form/"+id;
            }
        }
        
        return url;
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model,  @PathVariable("id") String id){
        
        kurirService.delete(id);

        model.addAttribute("content", "view/kurir/home");
        
        return "redirect:/kurir/";
    }
}
