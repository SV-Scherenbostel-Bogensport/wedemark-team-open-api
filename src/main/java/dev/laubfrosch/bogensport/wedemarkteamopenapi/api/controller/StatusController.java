package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    // GET /api/status - alle Status abrufen
    @GetMapping
    public ResponseEntity<List<Status>> getAllStatus() {
        List<Status> status = statusService.getAllStatus();
        return ResponseEntity.ok(status);
    }

    // GET /api/status/{id} - Status nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Status> getAllStatusById(@PathVariable Integer id) {
        Status staus = statusService.getStatusById(id);
        return  ResponseEntity.ok(staus);
    }
}
