package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/sets")
public class SetController {

    private final SetService setService;
    
    public SetController(SetService setService) {
        this.setService = setService;
    }

    // GET /api/sets - alle Sets abrufen
    @GetMapping
    public ResponseEntity<List<Set>> getAllSets() {
        List<Set> sets = setService.getAllSets();
        return ResponseEntity.ok(sets);
    }

    // GET /api/sets/{id} - Set nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Set> getSetById(@PathVariable Integer id) {
        Set set = setService.getSetById(id);
        return ResponseEntity.ok(set);

    }

    // PUT /api/sets - Set hinzufügen oder aktualisieren
    @PutMapping
    public ResponseEntity<Set> upsertSet(@RequestBody Set set) {
        Set saveSet = setService.upsertSet(set);
        return ResponseEntity.ok(saveSet);
    }

    // PUT /api/sets/batch - Mehrere Sets hinzufügen oder aktualisieren
    @PutMapping("/batch")
    public ResponseEntity<List<Set>> upsertSets(@RequestBody List<Set> sets) {
        List<Set> savedSets = setService.upsertSets(sets);
        return ResponseEntity.ok(savedSets);
    }

    // DELETE /api/sets/{id} - Set löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSet(@PathVariable Integer id) {
        setService.deleteSet(id);
        return ResponseEntity.noContent().build();
    }
}
