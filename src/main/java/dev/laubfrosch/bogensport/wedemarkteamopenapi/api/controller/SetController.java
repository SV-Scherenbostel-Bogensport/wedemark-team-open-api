package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
