package id.beecolony.routeanalyze.model;

import java.time.LocalDate;
import java.time.LocalTime;

import id.beecolony.routeanalyze.utils.base.BaseRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "laporan")
@Data
public class Laporan extends BaseRelation{

    private LocalDate tanggalLaporan;

    // Relasi dengan tabel Kurir
    @ManyToOne
    @JoinColumn(name = "kurir_id")
    private Kurir kurir;
}
