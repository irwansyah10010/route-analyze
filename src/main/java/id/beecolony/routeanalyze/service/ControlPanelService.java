package id.beecolony.routeanalyze.service;


import org.springframework.stereotype.Service;

@Service
public class ControlPanelService {

    // @Autowired
    // private Validator validator;

    // @Autowired
    // private WebAuthProvider authProvider;

    // public Map<String,String> authenticationUser(LoginPojo loginPojo){

    //     Map<String,String> message = new HashMap<>();
        
    //     Set<ConstraintViolation<LoginPojo>> violations = validator.validate(loginPojo);

    //     if(!violations.isEmpty()){
    //         for (ConstraintViolation<LoginPojo> constraintViolation : violations) {
    //             message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    //         }

    //         return message;
    //     }

    //     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginPojo.getUsername(), loginPojo.getPassword());
        
    //     Authentication authenticate = authProvider.authenticate(authenticationToken);
    //     SecurityContext securityContext = SecurityContextHolder.getContext();
    //     securityContext.setAuthentication(authenticate);
        
    //     return message;
    // }
    

}
