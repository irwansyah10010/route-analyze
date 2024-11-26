package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "pemasok")
@Data
public class Pemasok extends BaseMaster{
    @NotEmpty(message = "Nama tidak boleh kosong")
    private String namaPemasok;

    @NotEmpty(message = "Alamat tidak boleh kosong")
    private String alamat;

    private Boolean tandai;

    @NotNull(message = "Harap pilih lokasi pemasok")
    private Double latitude;

    private Double longitude;

    @PrePersist
    public void prePersistLocal(){
        tandai = false;
    }
}
