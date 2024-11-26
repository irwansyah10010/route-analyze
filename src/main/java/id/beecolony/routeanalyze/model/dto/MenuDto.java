package id.beecolony.routeanalyze.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MenuDto {
    
    private String id;

    @NotBlank(message = "Nama menu hanya spasi")
    @NotEmpty(message = "Nama menu tidak boleh kosong")
    private String name;

    @NotBlank(message = "Module hanya spasi")
    @NotEmpty(message = "Module tidak boleh kosong")
    private String module;

    private String parentId;

    private String createdBy;

    private LocalDateTime createdAt;

}
