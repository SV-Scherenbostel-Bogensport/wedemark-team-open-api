package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.StatusRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    // Alle Status laden
    public List<Status> getAllStatus() {
        return statusRepository.findAll(Sort.by("statusId"));
    }

    // Status nach ID finden
    public Status getStatusById(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status nicht gefunden mit ID: " + id));
    }
}
