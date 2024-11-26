package id.beecolony.routeanalyze.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.beecolony.routeanalyze.model.User;
import id.beecolony.routeanalyze.model.pojo.LoginPojo;
import id.beecolony.routeanalyze.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Component
public class WebAuthProvider implements AuthenticationProvider{

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User byEmail = userService.getByUsernameAndPassword(username, password);

        if(byEmail == null){
            Map<String, String> message = new HashMap<>();
            ObjectMapper om = new ObjectMapper();

            LoginPojo loginPojo = new LoginPojo();
            loginPojo.setUsername(username);
            loginPojo.setPassword(password);

            /* create validation */
            Set<ConstraintViolation<LoginPojo>> violations = validator.validate(loginPojo);

            if(!violations.isEmpty()){
                for (ConstraintViolation<LoginPojo> constraintViolation : violations) {
                    message.put("error"+constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                }
            }else{
                message.put("error", "Invalid login credentials");
            }

            try {
                throw new BadCredentialsException(om.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
            
        GrantedAuthority grantedAuthorityRole = new SimpleGrantedAuthority(byEmail.getRole().getRoleCode());
        

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthorityRole);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return new UsernamePasswordAuthenticationToken(byEmail, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
}
