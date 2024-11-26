package id.beecolony.routeanalyze.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.beecolony.routeanalyze.model.pojo.LoginPojo;
import id.beecolony.routeanalyze.service.ControlPanelService;

@Controller
@RequestMapping(value = "/")
public class ControlPanelController {

    @Autowired
    private ControlPanelService controlPanelService;
    
    @GetMapping(value = "login")
    public String login(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            return "redirect:/";
        }
        
        model.addAttribute("login", new LoginPojo());
        return "view/login";
    }

    @GetMapping
    public String dashboard(Model model){
        model.addAttribute("content", "view/home/home");
        
        return "view/index";
    }


}
