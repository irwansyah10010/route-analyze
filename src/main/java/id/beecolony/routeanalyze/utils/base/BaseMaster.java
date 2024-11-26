package id.beecolony.routeanalyze.utils.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseMaster extends BaseRelation{
    
    @Column(name = "is_active", nullable = false)
	private Boolean isActive;

    @PrePersist
    public void prePersistMaster(){
        this.isActive = true;
    }

}
