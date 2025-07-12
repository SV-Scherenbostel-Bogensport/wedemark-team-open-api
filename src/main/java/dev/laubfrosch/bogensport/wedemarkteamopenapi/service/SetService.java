package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.SetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
}
