package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TargetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TargetService {

    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    // Alle Targets laden
    public List<Target> getAllTargets() {
        return targetRepository.findAll();
    }

    // Target nach ID finden
    public Optional<Target> getTargetById(Integer id) {
        return targetRepository.findById(id);
    }

    // Target nach Code finden
    public Optional<Target> getTargetByCode(String code) {
        return targetRepository.findByCode(code);
    }

    // Neues Target erstellen
    public Target createTarget(Target target) {
        return targetRepository.save(target);
    }

    // Target aktualisieren
    public Optional<Target> updateTarget(Integer id, Target updatedTarget) {
        return targetRepository.findById(id).map(existing -> {
            existing.setCode(updatedTarget.getCode());
            return targetRepository.save(existing);
        });
    }

    // Target löschen
    public boolean deleteTarget(Integer id) {
        if (targetRepository.existsById(id)) {
            targetRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
