package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pengambilan_barang")
@Data
public class PengambilanBarang extends BaseRelation{

    private String namaBarang;

    private Integer jumlah;

    private Double jarak;

    // Relasi dengan tabel Laporan
    @ManyToOne
    @JoinColumn(name = "laporan_id")
    private Laporan laporan;

    // Relasi dengan tabel Pemasok
    @ManyToOne
    @JoinColumn(name = "pemasok_id")
    private Pemasok pemasok;
}
