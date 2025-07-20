package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Arrow;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.ArrowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArrowService {

    final ArrowRepository arrowRepository;

    public ArrowService(ArrowRepository arrowRepository) {
        this.arrowRepository = arrowRepository;
    }

    // Alle Pfeile laden
    public List<Arrow> getAllArrows() {
        return arrowRepository.findAll();
    }

    // Pfeil nach ID finden
    public Arrow getArrowById(Integer id) {
        return arrowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pfeil nicht gefunden mit ID: " + id));
    }
}
