package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.SetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SetService {

    final SetRepository setRepository;

    public SetService(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    // Alle Sets laden
    public List<Set> getAllSets() {
        return setRepository.findAll();
    }

    // Set nach ID finden
    public Set getSetById(Integer id) {
        return setRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Set nicht gefunden mit ID: " + id));
    }

    // Ein Set erstellen oder bestehenden bearbeiten
    public Set upsertSet(Set set) {
        Optional<Set> existingSet = setRepository.findByMatchIdAndSetIndex(
                set.getMatchId(),
                set.getSetIndex()
        );
        if (existingSet.isPresent()) {
            Set updateSet = existingSet.get();
            updateSet.setTotalTeam1(set.getTotalTeam1());
            updateSet.setTotalTeam2(set.getTotalTeam2());
            updateSet.setPointsTeam1(set.getPointsTeam1());
            updateSet.setPointsTeam2(set.getPointsTeam2());
            return setRepository.save(updateSet);
        } else {
            set.setSetId(null);
            return setRepository.save(set);
        }
    }

    //Set löschen
    public void deleteSet(Integer id) {
        if (!setRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Set nicht gefunden mit ID: " + id);
        }
        setRepository.deleteById(id);
    }
}
