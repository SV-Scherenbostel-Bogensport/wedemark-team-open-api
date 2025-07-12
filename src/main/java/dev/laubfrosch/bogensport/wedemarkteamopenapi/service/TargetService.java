package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TargetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public Target getTargetById(Integer id) {
        return targetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target nicht gefunden mit ID: " + id));
    }

    // Target nach Code finden
    public Target getTargetByCode(String code) {
        return targetRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target nicht gefunden mit Code: " + code));
    }

    // Neues Target erstellen
    public Target createTarget(Target target) {
        if (targetRepository.findByCode(target.getCode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target mit Code '" + target.getCode() + "' existiert bereits");
        }
        return targetRepository.save(target);
    }

    // Target aktualisieren
    public Target updateTarget(Integer id, Target updatedTarget) {
        Target existing = targetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target nicht gefunden mit ID: " + id));

        targetRepository.findByCode(updatedTarget.getCode()).ifPresent(target -> {
            if (!target.getTargetId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target mit Code '" + updatedTarget.getCode() + "' existiert bereits");
            }
        });

        existing.setCode(updatedTarget.getCode());
        return targetRepository.save(existing);
    }

    // Target löschen
    public void deleteTarget(Integer id) {
        if (!targetRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Target nicht gefunden mit ID: " + id);
        }
        targetRepository.deleteById(id);
    }
}