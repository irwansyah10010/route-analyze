package id.beecolony.routeanalyze.model;

import id.beecolony.routeanalyze.utils.base.BaseRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "inisial_solusion")
@Data
public class InisialSolusion extends BaseRelation{
    
    private Double fitness;

    private Double Solusi;

    @ManyToOne
    @JoinColumn(name = "pengambilan_barang_id")
    private PengambilanBarang pengambilanBarang;

    @ManyToOne
    @JoinColumn(name = "laporan_id")
    private Laporan laporan;
}
