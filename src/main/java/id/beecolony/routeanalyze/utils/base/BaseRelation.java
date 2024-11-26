package id.beecolony.routeanalyze.utils.base;


import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseRelation {

    @Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 32)
	private String id;
	
	@Column(name = "created_by", length = 32, nullable = true)
	private String createdBy;
	
	@Column(name = "created_at", nullable = true)
	private Long createdAt;
	
	@Column(name = "updated_by", length = 32, nullable = true)
	private String updatedBy;
	
	@Column(name = "updated_at", nullable = true)
	private Long updatedAt;
	
	@Version
	@Column(name = "versions", nullable = false)
	private Integer version;
	
    @PrePersist
    public void prePersistRelation(){
        this.createdAt = System.currentTimeMillis();
		this.createdBy = "System";
    }

    @PreUpdate
    public void preUpdateRelation(){
        this.updatedAt = System.currentTimeMillis();
		this.updatedBy = "System";
    }
}
