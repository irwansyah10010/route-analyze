package id.beecolony.routeanalyze.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.beecolony.routeanalyze.model.Pengaturan;
import id.beecolony.routeanalyze.service.PengaturanService;

@Controller
@RequestMapping("pengaturan/")
public class PengaturanController {
    
    @Autowired
    private PengaturanService pengaturanService;

    @GetMapping(value = "")
    public String index(Model model){

        model.addAttribute("pengaturanBcos", pengaturanService.getByBcoTipe());
        model.addAttribute("pengaturanParams", pengaturanService.getByParamTipe());
        model.addAttribute("pengaturan", new Pengaturan());
        model.addAttribute("content", "view/pengaturan/home");

        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute Pengaturan pengaturan, RedirectAttributes redirectAttributes){

        Map<String,String> saves = pengaturanService.save(pengaturan);

        String url = "";

        if(saves.isEmpty()){
            model.addAttribute("content", "view/pengaturan/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/pengaturan/";
        }else{

            for (Entry<String,String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error"+message.getKey(), message.getValue());
            }

            model.addAttribute("pengaturan", pengaturan);
            model.addAttribute("content", "view/pengaturan/form");

            String id = pengaturan.getId();

            if(id.isEmpty()){
                url = "redirect:/pengaturan/form/sa";
            }else{
                url = "redirect:/pengaturan/form/"+id;
            }
        }

        return url;
    }

}
