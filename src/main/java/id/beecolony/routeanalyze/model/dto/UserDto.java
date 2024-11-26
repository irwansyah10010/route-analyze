package id.beecolony.routeanalyze.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    
    private String id;

    @NotBlank(message = "Full Name hanya spasi")
    @NotEmpty(message = "Full Name tidak boleh kosong")
	private String fullName;

    @NotBlank(message = "Password hanya spasi")
    @NotEmpty(message = "Password tidak boleh kosong")
    private String password;
	
    @NotBlank(message = "Tempat lahir hanya spasi")
    @NotEmpty(message = "Tempat lahir tidak boleh kosong")
	private String placeOfBirth;
	
    @NotNull(message = "Tanggal lahir tidak boleh kosong")
	private LocalDate dateOfBirth;
	
	private String address;
	
    @NotEmpty(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak sesuai")
	private String email;

    @NotEmpty(message = "Pilih salah satu role")
    private String roleId;

    private String roleName;

    private String createdBy;

    private LocalDateTime createdAt;

}
