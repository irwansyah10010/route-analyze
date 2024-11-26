package id.beecolony.routeanalyze.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LaporanDto {
    
    private String id;

    @NotNull(message = "Tanggal Laporan tidak boleh kosong")
    private LocalDate tanggalLaporan;

    @NotNull(message = "Harap pilih salah satu kurir")
    @NotEmpty(message = "Harap pilih salah satu kurir")
    private String kurirId;

    private String namaKurir;

    @NotNull(message = "Pengambilan Barang tidak tersedia")
    private List<PengambilanBarangDto> pengambilanBarangDtos;

    private String createdBy;

    private LocalDateTime createdAt;

    private Boolean isCalculateSolusion;
}
