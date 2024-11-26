package id.beecolony.routeanalyze.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.beecolony.routeanalyze.model.InisialSolusion;
import id.beecolony.routeanalyze.model.Kurir;
import id.beecolony.routeanalyze.model.Laporan;
import id.beecolony.routeanalyze.model.Pemasok;
import id.beecolony.routeanalyze.model.PengambilanBarang;
import id.beecolony.routeanalyze.model.PengaturanBco;
import id.beecolony.routeanalyze.model.dto.InisialSolusionDto;
import id.beecolony.routeanalyze.model.dto.LaporanDto;
import id.beecolony.routeanalyze.model.dto.PengambilanBarangDto;
import id.beecolony.routeanalyze.repository.InisialSolusionRepository;
import id.beecolony.routeanalyze.repository.KurirRepository;
import id.beecolony.routeanalyze.repository.LaporanRepository;
import id.beecolony.routeanalyze.repository.PemasokRepository;
import id.beecolony.routeanalyze.repository.PengambilanBarangRepository;
import id.beecolony.routeanalyze.repository.PengaturanBcoRepository;
import id.beecolony.routeanalyze.utils.BeeColonyOptimization;
import id.beecolony.routeanalyze.utils.HaversineUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class LaporanService {
    
    @Autowired
    private LaporanRepository laporanRepository;

    @Autowired
    private KurirRepository kurirRepository;

    @Autowired
    private PengambilanBarangRepository pengambilanBarangRepository;

    @Autowired
    private PemasokRepository pemasokRepository;

    @Autowired
    private HaversineUtils haversineUtils;

    @Autowired
    private BeeColonyOptimization beeColonyOptimization;

    @Autowired
    private PengaturanBcoRepository pengaturanBcoRepository;

    @Autowired
    private InisialSolusionRepository inisialSolusionRepository;

    @Autowired
    private Validator validator;

    public List<LaporanDto> getAllV2() {
        List<Laporan> laporans = laporanRepository.findAll();
        List<LaporanDto> laporanDtos = new ArrayList<>();

        for (Laporan laporan : laporans) {
            LaporanDto laporanDto = new LaporanDto();

            LocalDateTime localDateTime = Instant.ofEpochMilli(laporan.getCreatedAt())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

            laporanDto.setId(laporan.getId());
            laporanDto.setTanggalLaporan(laporan.getTanggalLaporan());
            laporanDto.setCreatedAt(localDateTime);
            laporanDto.setCreatedBy(laporan.getCreatedBy());
            laporanDto.setIsCalculateSolusion(inisialSolusionRepository.getAllByLaporanIdOrderByFitnessDesc(laporan.getId()).size() > 0);

            Kurir kurir = laporan.getKurir();
            laporanDto.setKurirId(kurir.getId());
            laporanDto.setNamaKurir(kurir.getNama());
            
            List<PengambilanBarang> pengambilanBarangs = pengambilanBarangRepository.getAllByLaporanId(laporan.getId());
            List<PengambilanBarangDto> pengambilanBarangDtos = new ArrayList<>();

            for (PengambilanBarang pengambilanBarang : pengambilanBarangs) {
                PengambilanBarangDto pengambilanBarangDto = new PengambilanBarangDto();

                pengambilanBarangDto.setId(pengambilanBarang.getId());
                pengambilanBarangDto.setNamaBarang(pengambilanBarang.getNamaBarang());
                pengambilanBarangDto.setJumlah(pengambilanBarang.getJumlah());
                pengambilanBarangDto.setJarak(pengambilanBarang.getJarak());

                Pemasok pemasok = pengambilanBarang.getPemasok();

                pengambilanBarangDto.setPemasokId(pemasok.getId());
                pengambilanBarangDto.setNamaPemasok(pemasok.getNamaPemasok());
                pengambilanBarangDto.setAlamatPemasok(pemasok.getAlamat());


                pengambilanBarangDtos.add(pengambilanBarangDto);
            }

            laporanDto.setPengambilanBarangDtos(pengambilanBarangDtos);
            laporanDtos.add(laporanDto);
        }

        return laporanDtos;

    }

    public List<Laporan> getAll() {
        return laporanRepository.findAll();
    }

    public LaporanDto getById(String id) {
        Optional<Laporan> laporanById = laporanRepository.findById(id);
        LaporanDto laporanDto = new LaporanDto();
        List<PengambilanBarangDto> pengambilanBarangDtos = new ArrayList<>();

        if (laporanById.isPresent()) {
            Laporan laporan = laporanById.get();
            laporanDto.setKurirId(laporan.getKurir().getId());
            laporanDto.setNamaKurir(laporan.getKurir().getNama());
            laporanDto.setTanggalLaporan(laporan.getTanggalLaporan());

            List<PengambilanBarang> pengambilanBarangByLaporans = pengambilanBarangRepository.getAllByLaporanId(id);
            for (PengambilanBarang pengambilanBarang : pengambilanBarangByLaporans) {
                PengambilanBarangDto pengambilanBarangDto = new PengambilanBarangDto();

                pengambilanBarangDto.setLaporanId(pengambilanBarang.getLaporan().getId());
                pengambilanBarangDto.setJumlah(pengambilanBarang.getJumlah());
                pengambilanBarangDto.setNamaBarang(pengambilanBarang.getNamaBarang());
                pengambilanBarangDto.setJarak(pengambilanBarang.getJarak());
                pengambilanBarangDto.setPemasokId(pengambilanBarang.getPemasok().getId());

                pengambilanBarangDtos.add(pengambilanBarangDto);
            }
        }

        laporanDto.setPengambilanBarangDtos(pengambilanBarangDtos);
        
        return laporanDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String,String> save(LaporanDto laporanDto) {

        Map<String,String> message = new HashMap<>();

        Pemasok pemasokSaya = pemasokRepository.findById("my-location").get();
        
        /* validasi */
        Set<ConstraintViolation<LaporanDto>> violations = validator.validate(laporanDto);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<LaporanDto> constraintViolation : violations) {
                message.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return message;
        }

        List<PengambilanBarangDto> pengambilanBarangDtos = laporanDto.getPengambilanBarangDtos();

        Set<ConstraintViolation<PengambilanBarangDto>> violationPengambilans = null;
        StringBuilder messagePengambilan = new StringBuilder();

        int index = 1;
        for (PengambilanBarangDto pengambilanBarangDto : pengambilanBarangDtos) {
            violationPengambilans = validator.validate(pengambilanBarangDto);

            if (!violationPengambilans.isEmpty()) {
                for (ConstraintViolation<PengambilanBarangDto> constraintViolation : violationPengambilans) {
                    messagePengambilan.append(constraintViolation.getMessage())
                    .append(" pada Input ke-")
                    .append(index)
                    .append(" <br><br>");
                }
            }

            index++;
        }
        
        if (!violationPengambilans.isEmpty()) {
            message.put("pengambilan", messagePengambilan.toString());
            return message;
        }

        /* */
        Laporan laporanNew = null;
        String id = laporanDto.getId();

        if (!id.isEmpty()) {
            laporanNew = laporanRepository.findById(id).get();
            laporanNew.setUpdatedBy("System");
        } else {
            laporanNew = new Laporan();
        }

        Optional<Kurir> kurirById = kurirRepository.findById(laporanDto.getKurirId());

        laporanNew.setTanggalLaporan(laporanDto.getTanggalLaporan());
        laporanNew.setKurir(kurirById.isPresent()?kurirById.get():null);

        Laporan laporanSave = laporanRepository.save(laporanNew);

        if (laporanSave != null) {
            List<PengambilanBarang> pengambilanBarangs = new ArrayList<>();

            for (PengambilanBarangDto pengambilanBarangDto : pengambilanBarangDtos) {
                PengambilanBarang pengambilanBarang = new PengambilanBarang();

                pengambilanBarang.setJumlah(pengambilanBarangDto.getJumlah());
                pengambilanBarang.setLaporan(laporanSave);
                pengambilanBarang.setNamaBarang(pengambilanBarangDto.getNamaBarang());

                Pemasok pemasok = pemasokRepository.findById(pengambilanBarangDto.getPemasokId()).get();

                double calculateHaversine = haversineUtils.calculate(pemasok.getLatitude(), pemasok.getLongitude(),
                                                pemasokSaya.getLatitude(), pemasokSaya.getLongitude());

                pengambilanBarang.setJarak(calculateHaversine);
                pengambilanBarang.setPemasok(pemasok);
                pengambilanBarangs.add(pengambilanBarang);
            }

            List<PengambilanBarang> saveAll = pengambilanBarangRepository.saveAll(pengambilanBarangs);

            if(saveAll.isEmpty()){
                message.put("error", "Laporan tidak dapat disimpan");
            }
        }

        return message;
    }

    public Map<String, String> rute(String laporanId){
        
        Map<String,String> message = new HashMap<>();

        List<PengambilanBarang> allByLaporanId = pengambilanBarangRepository.getAllByLaporanId(laporanId);

        List<InisialSolusionDto> solusionDtos = beeColonyOptimization.calculate(allByLaporanId, laporanId);
        List<InisialSolusion> solusions = new ArrayList<>();

        for (InisialSolusionDto inisialSolusionDto : solusionDtos) {
            InisialSolusion inisialSolusion = new InisialSolusion();
            inisialSolusion.setSolusi(inisialSolusionDto.getSolusi());
            inisialSolusion.setFitness(inisialSolusionDto.getFitness());
            
            Optional<Laporan> laporanAllById = laporanRepository.findById(inisialSolusionDto.getLaporanId());;
            inisialSolusion.setLaporan(laporanAllById.get());

            Optional<PengambilanBarang> pengambilanBarangAllById = pengambilanBarangRepository.findById(inisialSolusionDto.getPengambilanBarangId());
            inisialSolusion.setPengambilanBarang(pengambilanBarangAllById.get());

            solusions.add(inisialSolusion);
        }

        List<InisialSolusion> saveAll = inisialSolusionRepository.saveAll(solusions);

        if(saveAll.isEmpty())
            message.put("error", "Rute tidak dapat disimpan");

        return message;
    }

    @Transactional
    public boolean delete(String id) {
        pengambilanBarangRepository.removeAllByLaporanId(id);
        laporanRepository.deleteById(id);

        return true;
    }

}
