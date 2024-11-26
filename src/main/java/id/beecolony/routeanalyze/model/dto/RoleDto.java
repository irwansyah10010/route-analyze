package id.beecolony.routeanalyze.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleDto {
    
    private String id;

    @NotBlank(message = "Code role hanya spasi")
    @NotEmpty(message = "Code role tidak boleh kosong")
    private String roleCode;

    @NotBlank(message = "Nama role hanya spasi")
    @NotEmpty(message = "Nama role tidak boleh kosong")
    private String roleName;

    private String createdBy;

    private LocalDateTime createdAt;

}
