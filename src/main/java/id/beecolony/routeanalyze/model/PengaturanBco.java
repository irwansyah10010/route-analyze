package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pengaturan_bco")
@Data
public class PengaturanBco extends BaseMaster{
 
    private Integer maxIterations; // Maksimum iterasi
    
    private Integer populationSize; // Ukuran populasi

    private Integer employedBees; // Jumlah lebah pekerja

    private Integer onlookerBees; // Jumlah lebah pengamat

    private Integer scoutBees;// Jumlah lebah pencari

    private String objective; // Tujuan optimasi

    @ManyToOne
    @JoinColumn(name = "laporan_id")
    private Laporan laporan;
}
