package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Arrow;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.ArrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/arrows")
public class ArrowController {

    private final ArrowService arrowService;

    public ArrowController(ArrowService arrowService) {
        this.arrowService = arrowService;
    }

    // GET /api/rounds - alle Runden abrufen
    @GetMapping
    public ResponseEntity<List<Arrow>> getAllArrows(){
        List<Arrow> arrows = arrowService.getAllArrows();
        return ResponseEntity.ok(arrows);
    }

    // GET /api/rounds/{id} - Runde nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Arrow> getArrowById(@PathVariable Integer id) {
        Arrow arrow = arrowService.getArrowById(id);
        return ResponseEntity.ok(arrow);
    }
}
