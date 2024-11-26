package id.beecolony.routeanalyze.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.beecolony.routeanalyze.model.PengambilanBarang;
import id.beecolony.routeanalyze.model.Pengaturan;
import id.beecolony.routeanalyze.model.PengaturanBco;
import id.beecolony.routeanalyze.model.dto.InisialSolusionDto;
import id.beecolony.routeanalyze.repository.PengaturanRepository;

@Component
public class BeeColonyOptimization {

    // Parameter Algoritma
    private static final double LOWER_BOUND = -10.0; // Batas bawah solusi
    private static final double UPPER_BOUND = 10.0;  // Batas atas solusi
    private final Random rand = new Random(); // ekplorasi baru

    @Autowired
    private PengaturanRepository pengaturanRepository;

    // Fungsi yang akan dioptimasi (contoh: Fungsi Sphere)
    public double objectiveFunction(double x) {
        return x * x; // Fungsi sphere sederhana f(x) = x^2
    }

    // Fungsi untuk mencari solusi acak dalam batas yang diberikan
    public double randomSolution() {
        return BeeColonyOptimization.LOWER_BOUND + (BeeColonyOptimization.UPPER_BOUND - BeeColonyOptimization.LOWER_BOUND) * rand.nextDouble();
    }

    // Algoritma BCO Utama
    // public List<InisialSolusionDto> calculate(List<PengambilanBarang> pengambilanBarangs, String laporanId, PengaturanBco pengaturanBco) {
    //     int size = pengambilanBarangs.size();

    //     List<InisialSolusionDto> inisialSolusions = new ArrayList<>();

    //     // Inisialisasi solusi secara acak
    //     for (int i = 0; i < size; i++) {
    //         InisialSolusionDto inisialSolusion = new InisialSolusionDto();
    //         PengambilanBarang pengambilanBarang = pengambilanBarangs.get(i);
    //         inisialSolusion.setSolusi(pengambilanBarang.getJarak());
    //         inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));

    //         inisialSolusion.setPengambilanBarangId(pengambilanBarang.getId());
    //         inisialSolusion.setLaporanId(laporanId);

    //         inisialSolusions.add(inisialSolusion);
    //     }

    //     for (int iter = 0; iter < pengaturanBco.getMaxIterations(); iter++) {
            
    //         // Employed bee phase
    //         for (int i = 0; i < pengaturanBco.getEmployedBees(); i++) {
    //             InisialSolusionDto inisialSolusion = inisialSolusions.get(i);

    //             double newSolution = inisialSolusion.getSolusi() + (rand.nextDouble() - 0.5); // Eksplorasi solusi baru
    //             if (newSolution < BeeColonyOptimization.LOWER_BOUND || newSolution > BeeColonyOptimization.UPPER_BOUND) {
    //                 newSolution = randomSolution(); // Jika solusi baru keluar dari batas
    //             }

    //             double newFitness = objectiveFunction(newSolution);
    //             if (newFitness < inisialSolusion.getFitness()) { // Mempertahankan solusi yang lebih baik
    //                 inisialSolusion.setSolusi(newSolution);
    //                 inisialSolusion.setFitness(newFitness);
    //             }
    //         }

    //         // Onlooker bee phase (berdasarkan probabilitas solusi terbaik)
    //         double totalFitness = 0;
            
    //         for (int i = 0; i < pengaturanBco.getOnlookerBees(); i++) {
    //             InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
    //             totalFitness += inisialSolusion.getFitness();
    //         }

    //         for (int i = 0; i < pengaturanBco.getOnlookerBees(); i++) {
    //             InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
    //             double probability = inisialSolusion.getFitness() / totalFitness;
    //             if (rand.nextDouble() < probability) {
    //                 double newSolution = inisialSolusion.getSolusi() + (rand.nextDouble() - 0.5);
    //                 double newFitness = objectiveFunction(newSolution);
    //                 if (newFitness < inisialSolusion.getFitness()) {
    //                     inisialSolusion.setSolusi(newSolution);
    //                     inisialSolusion.setFitness(newFitness);
    //                 }
    //             }
    //         }

    //         // Scout bee phase (mencari solusi baru secara acak)
            
    //         for (int i = 0; i < pengaturanBco.getScoutBees(); i++) {
    //             InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
    //             if (inisialSolusion.getFitness() == objectiveFunction(inisialSolusion.getSolusi())) {
    //                 inisialSolusion.setSolusi(randomSolution());
    //                 inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));
    //             }
    //         }

    //         for (int i = 0; i < pengaturanBco.getScoutBees(); i++) {
    //             InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
    //             if (inisialSolusion.getFitness() == objectiveFunction(inisialSolusion.getSolusi())) {
    //                 inisialSolusion.setSolusi(randomSolution());
    //                 inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));
    //             }
    //         }

    //         // Employed bee phase
            

            
    //     }

    //     return inisialSolusions;
    // }


    public List<InisialSolusionDto> calculate(List<PengambilanBarang> pengambilanBarangs, String laporanId) {
        int size = pengambilanBarangs.size();

        List<InisialSolusionDto> inisialSolusions = new ArrayList<>();
        Optional<Pengaturan> trafficCondition = pengaturanRepository.findById("traffic_conditions");
        Optional<Pengaturan> weatherCondition = pengaturanRepository.findById("weather_conditions");
        Optional<Pengaturan> vehicleType = pengaturanRepository.findById("vehicle_type");

        int maxIteration = size;
        int employedBees = size;
        int onlookerBees = size;
        int scoutBees = size;


        // Inisialisasi solusi secara acak
        for (int i = 0; i < size; i++) {
            InisialSolusionDto inisialSolusion = new InisialSolusionDto();
            PengambilanBarang pengambilanBarang = pengambilanBarangs.get(i);
            inisialSolusion.setSolusi(pengambilanBarang.getJarak());
            inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));

            inisialSolusion.setPengambilanBarangId(pengambilanBarang.getId());
            inisialSolusion.setLaporanId(laporanId);

            inisialSolusions.add(inisialSolusion);
        }

        for (int iter = 0; iter < maxIteration; iter++) {
            
            double explorationValue = (rand.nextDouble() - 0.5)  * Double.valueOf(trafficCondition.get().getValue()) * Double.valueOf(weatherCondition.get().getValue()) * Double.valueOf(vehicleType.get().getValue());
            
            // Employed bee phase
            for (int i = 0; i < employedBees; i++) {
                InisialSolusionDto inisialSolusion = inisialSolusions.get(i);

                double newSolution = inisialSolusion.getSolusi() + explorationValue; // Eksplorasi solusi baru
                if (newSolution < BeeColonyOptimization.LOWER_BOUND || newSolution > BeeColonyOptimization.UPPER_BOUND) {
                    newSolution = randomSolution(); // Jika solusi baru keluar dari batas
                }

                double newFitness = objectiveFunction(newSolution);
                if (newFitness < inisialSolusion.getFitness()) { // Mempertahankan solusi yang lebih baik
                    inisialSolusion.setSolusi(newSolution);
                    inisialSolusion.setFitness(newFitness);
                }
            }

            // Onlooker bee phase (berdasarkan probabilitas solusi terbaik)
            double totalFitness = 0;
            
            for (int i = 0; i < onlookerBees; i++) {
                InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
                totalFitness += inisialSolusion.getFitness();
            }

            for (int i = 0; i < onlookerBees; i++) {
                InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
                double probability = inisialSolusion.getFitness() / totalFitness;
                if (rand.nextDouble() < probability) {
                    double newSolution = inisialSolusion.getSolusi() + (rand.nextDouble() - 0.5);
                    double newFitness = objectiveFunction(newSolution);
                    if (newFitness < inisialSolusion.getFitness()) {
                        inisialSolusion.setSolusi(newSolution);
                        inisialSolusion.setFitness(newFitness);
                    }
                }
            }

            // Scout bee phase (mencari solusi baru secara acak)
            for (int i = 0; i < scoutBees; i++) {
                InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
                if (inisialSolusion.getFitness() == objectiveFunction(inisialSolusion.getSolusi())) {
                    inisialSolusion.setSolusi(randomSolution());
                    inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));
                }
            }

            for (int i = 0; i < scoutBees; i++) {
                InisialSolusionDto inisialSolusion = inisialSolusions.get(i);
                if (inisialSolusion.getFitness() == objectiveFunction(inisialSolusion.getSolusi())) {
                    inisialSolusion.setSolusi(randomSolution());
                    inisialSolusion.setFitness(objectiveFunction(inisialSolusion.getSolusi()));
                }
            }
        }

        return inisialSolusions;
    }

}

