package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.TargetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/targets")
public class TargetController {

    private final TargetService targetService;

    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    // GET /api/targets - alle Targets abrufen
    @GetMapping
    public ResponseEntity<List<Target>> getAllTargets() {
        List<Target> targets = targetService.getAllTargets();
        return ResponseEntity.ok(targets);
    }

    // GET /api/targets/{id} - Target nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Target> getTargetById(@PathVariable Integer id) {
        Target target = targetService.getTargetById(id);
        return ResponseEntity.ok(target);
    }

    // GET /api/targets/code/{code} - Target nach Code abrufen
    @GetMapping("/code/{code}")
    public ResponseEntity<Target> getTargetByCode(@PathVariable String code) {
        Target target = targetService.getTargetByCode(code);
        return ResponseEntity.ok(target);
    }

    // POST /api/targets - neues Target erstellen
    @PostMapping
    public ResponseEntity<Target> createTarget(@RequestBody Target target) {
        Target createdTarget = targetService.createTarget(target);
        return ResponseEntity.ok(createdTarget);
    }

    // PUT /api/targets/{id} - Target aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Target> updateTarget(@PathVariable Integer id, @RequestBody Target updatedTarget) {
        Target target = targetService.updateTarget(id, updatedTarget);
        return ResponseEntity.ok(target);
    }

    // DELETE /api/targets/{id} - Target löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarget(@PathVariable Integer id) {
        targetService.deleteTarget(id);
        return ResponseEntity.noContent().build();
    }
}
