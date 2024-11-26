package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Entity
@Table(name = "kurir")
@Data
public class Kurir extends BaseMaster{

    @NotEmpty(message = "Nama tidak boleh kosong")
    private String nama;
    
    @NotEmpty(message = "Nomor Telepon tidak boleh kosong")
    private String nomorTelepon;
    
}

