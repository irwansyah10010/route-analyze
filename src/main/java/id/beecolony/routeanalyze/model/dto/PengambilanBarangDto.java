package id.beecolony.routeanalyze.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PengambilanBarangDto {
    private String id;

    @NotEmpty(message = "Nama barang tidak boleh kosong")
    private String namaBarang;

    @NotNull(message = "Jumlah tidak boleh kosong")
    private Integer jumlah;

    private Double jarak;

    private String laporanId;

    @NotEmpty(message = "Harap pilih salah satu pemasok")
    private String pemasokId;

    private String namaPemasok;

    private String alamatPemasok;
}
