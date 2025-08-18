package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CreateArrowDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Arrow;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.ArrowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/arrows")
public class ArrowController {

    private final ArrowService arrowService;

    public ArrowController(ArrowService arrowService) {
        this.arrowService = arrowService;
    }

    // GET /api/arrows - alle Pfeile abrufen
    @GetMapping
    public ResponseEntity<List<Arrow>> getAllArrows(){
        List<Arrow> arrows = arrowService.getAllArrows();
        return ResponseEntity.ok(arrows);
    }

    // GET /api/arrows/{id} - Pfeil nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Arrow> getArrowById(@PathVariable Integer id) {
        Arrow arrow = arrowService.getArrowById(id);
        return ResponseEntity.ok(arrow);
    }


    // PUT /api/arrows - neues Match erstellen
    @PutMapping
    public ResponseEntity<Arrow> upsertSet(@RequestBody CreateArrowDto arrow) {
        Arrow saveArrow = arrowService.upsertArrow(arrow);
        return ResponseEntity.ok(saveArrow);
    }


}
