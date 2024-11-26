package id.beecolony.routeanalyze.model.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginPojo {

    @NotEmpty(message = "Please fill Username")
    @NotBlank(message = "Please fill Username")
    private String username;


    @NotEmpty(message = "Please fill Password")
    @NotBlank(message = "Please fill Password")
    private String password;
    
}
