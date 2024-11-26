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

import id.beecolony.routeanalyze.model.PengaturanBco;
import id.beecolony.routeanalyze.service.PengaturanBcoService;

@Controller
@RequestMapping(value = "pengaturan-bco/")
public class PengaturanBcoController {
    
    @Autowired
    private PengaturanBcoService pengaturanBcoService;

    @GetMapping(value = "{id}")
    public String index(Model model, @PathVariable("id") String id){
        List<PengaturanBco> allByLaporanId = pengaturanBcoService.getAllByLaporanId(id);
        model.addAttribute("pengaturanBCO", allByLaporanId.get(0));
        model.addAttribute("content", "view/pengaturanbco/home");

        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute PengaturanBco pengaturanBco, RedirectAttributes redirectAttributes){
        
        Map<String,String> saves = pengaturanBcoService.save(pengaturanBco);

        if(saves.isEmpty()){
            

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");
        } else {
            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("pengaturanBco", pengaturanBco);
        }

        model.addAttribute("content", "view/pengaturanbco/home");
        
        return "redirect:/pengaturan-bco/";
    }

}
