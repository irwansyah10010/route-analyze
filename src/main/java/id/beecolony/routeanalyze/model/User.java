package id.beecolony.routeanalyze.model;

import java.time.LocalDate;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
public class User extends BaseMaster{
    
    @Column(name = "full_name", length = 50, nullable = false)
	private String fullName;
	
	@Column(name = "place_of_birth", length = 30, nullable = false)
	private String placeOfBirth;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "address", length = 100, nullable = false)
	private String address;
	
	@Column(name = "email", length = 30, nullable = false)
	private String email;
	
	@Column(name="pass", length = 32, nullable = false)
	private String pass;
	
    @ManyToOne
	@JoinColumn(name="role_id", columnDefinition = "varchar(32)")
	private Role role;	
}
