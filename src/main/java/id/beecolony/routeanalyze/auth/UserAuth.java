package id.beecolony.routeanalyze.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import id.beecolony.routeanalyze.model.User;

@Component
public class UserAuth {
    
    public User userPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            User userPrincipal = (User) authentication.getPrincipal();

            return userPrincipal;
        }

        return null;
    }
}

