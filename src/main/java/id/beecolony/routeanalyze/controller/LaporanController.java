package id.beecolony.routeanalyze.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.beecolony.routeanalyze.model.Kurir;
import id.beecolony.routeanalyze.model.Pemasok;
import id.beecolony.routeanalyze.model.PengambilanBarang;
import id.beecolony.routeanalyze.model.dto.LaporanDto;
import id.beecolony.routeanalyze.service.InisialSolusionService;
import id.beecolony.routeanalyze.service.KurirService;
import id.beecolony.routeanalyze.service.LaporanService;
import id.beecolony.routeanalyze.service.PemasokService;
import id.beecolony.routeanalyze.service.PengambilanBarangService;

@Controller
@RequestMapping(value = "laporan/")
public class LaporanController {
    
    @Autowired
    private LaporanService laporanService;

    @Autowired
    private InisialSolusionService inisialSolusionService;

    @Autowired
    private KurirService kurirService;

    @Autowired
    private PemasokService pemasokService;

    @Autowired
    private PengambilanBarangService pengambilanBarangService;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("laporans", laporanService.getAllV2());
        model.addAttribute("content", "view/laporan/home");

        return "view/index";
    }
    
    @ResponseBody
    @GetMapping(value = "pengambilan-barang/{laporanId}")
    public ResponseEntity<List<PengambilanBarang>> getByLaporan(@PathVariable("laporanId") String laporanId){
        return new ResponseEntity<>(pengambilanBarangService.getByLaporanId(laporanId), HttpStatus.OK);
    }

    @GetMapping(value = "inisial-solusion/{id}")
    public String inisialSolusion(Model model, @PathVariable("id") String id) {
        model.addAttribute("laporan", laporanService.getById(id));
        model.addAttribute("inisialSolusions", inisialSolusionService.getByLaporan(id));
        model.addAttribute("content", "view/laporan/home-inisial-solusion");

        return "view/index";
    }

    @GetMapping(value = "form/{id}")
    public String formBy(Model model, @PathVariable("id") String id) {

        LaporanDto laporanById = laporanService.getById(id);
        List<Kurir> kurirs = kurirService.getAll();
        List<Pemasok> pemasoks = pemasokService.getAll();

        String pemasokJson = "";
        ObjectMapper ob = new ObjectMapper();
        try {
            pemasokJson = ob.writeValueAsString(pemasoks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        };

        model.addAttribute("laporan", laporanById);
        model.addAttribute("kurirs", kurirs);
        model.addAttribute("pemasoks", pemasoks);
        model.addAttribute("pemasoksJs", pemasokJson);
        model.addAttribute("content", "view/laporan/form");
        
        return "view/index";
    }

    @PostMapping(value = "addedit/")
    public String addedit(Model model, @ModelAttribute LaporanDto laporanDto, RedirectAttributes redirectAttributes) {
        
        Map<String,String> saves = laporanService.save(laporanDto);

        String url = "";

        if (saves.isEmpty()) {
            model.addAttribute("content", "view/laporan/home");

            redirectAttributes.addFlashAttribute("notification", "Data tersimpan");

            url = "redirect:/laporan/";
        } else {
            for (Entry<String, String> message : saves.entrySet()) {
                redirectAttributes.addFlashAttribute("error" + message.getKey(), message.getValue());
            }

            model.addAttribute("laporan", laporanDto);
            model.addAttribute("content", "view/laporan/form");

            String id = laporanDto.getId();

            if (id.isEmpty()) {
                url = "redirect:/laporan/form/sa";
            } else {
                url = "redirect:/laporan/form/" + id;
            }
        }
        
        return url;
    }

    @GetMapping("rute/{id}")
    public String rute(Model model, @PathVariable("id") String id, RedirectAttributes redirectAttributes){

        Map<String,String> rutes = laporanService.rute(id);

        if (rutes.isEmpty()) {
            model.addAttribute("content", "view/laporan/home");

            redirectAttributes.addFlashAttribute("notification", "Rute tersimpan");
        }else{
            redirectAttributes.addFlashAttribute("notification", "Data gagal tersimpan");
        }

        return "redirect:/laporan/";
    }

    @GetMapping(value = "delete/{id}")
    public String delete(Model model, @PathVariable("id") String id) {
        
        laporanService.delete(id);

        model.addAttribute("content", "view/laporan/home");
        
        return "redirect:/laporan/";
    }

}
