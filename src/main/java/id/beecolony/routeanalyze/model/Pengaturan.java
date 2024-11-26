package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "pengaturan")
@Data
public class Pengaturan extends BaseMaster{
    private String tipe;

    private String label;

    @NotEmpty(message = "field is not null")
    private String value;

    private String lov;

    private String format;

    private String deskripsi;

    @Transient
    private String taggingValue;
}
