package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    // Alle Status laden
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    // Status nach ID finden
    public Optional<Status> getStatusById(Integer id) {
        return statusRepository.findById(id);
    }
}
