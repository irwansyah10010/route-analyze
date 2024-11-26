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

import id.beecolony.routeanalyze.model.Pemasok;
import id.beecolony.routeanalyze.service.PemasokService;

@Controller
@RequestMapping(value = "pemasok/")
public class PemasokController {
    
    @Autowired
    private PemasokService pemasokService;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("pemasoks", pemasokService.getAll());
        model.addAttribute("modaldelete", "view/template/modal-delete");
        model.addAttribute("content", "view/pemasok/home");

        return "view/index";
    }

    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id) {

        Pemasok pemasokById = pemasokService.getById(id);

        model.addAttribute("pemasok", pemasokById);
        model.addAttribute("content", "view/pemasok/form");

        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute Pemasok pemasok, RedirectAttributes redirectAttributes) {

        Map<String, String> saves = pemasokService.save(pemasok);

        String url = "";

        if (saves.isEmpty()) {
            model.addAttribute("content", "view/pemasok/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/pemasok/";
        } else {

            for (Entry<String, String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error" + message.getKey(), message.getValue());
            }

            model.addAttribute("pemasok", pemasok);
            model.addAttribute("content", "view/pemasok/form");

            String id = pemasok.getId();

            if (id.isEmpty()) {
                url = "redirect:/pemasok/form/sa";
            } else {
                url = "redirect:/pemasok/form/" + id;
            }
        }

        return url;
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model, @PathVariable("id") String id) {

        pemasokService.delete(id);

        model.addAttribute("content", "view/pemasok/home");

        return "redirect:/pemasok/";
    }

}
