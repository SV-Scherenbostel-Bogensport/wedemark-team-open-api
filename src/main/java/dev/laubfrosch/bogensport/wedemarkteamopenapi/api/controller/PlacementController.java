package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.*;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.PlacementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/placement")
public class PlacementController {

    private final PlacementService placementService;

    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;
    }

    // GET /api/placement/qualification - Die Platzierung
    @GetMapping("qualification")
    public ResponseEntity<QualificationPlacementResponse> getQualificationPlacement() {
        QualificationPlacementResponse placement = placementService.getQualificationPlacement();
        return ResponseEntity.ok(placement);
    }

    // GET /api/placement/final
    @GetMapping("final")
    public ResponseEntity<FinalPlacementResponse> getFinalPlacement() {
        FinalPlacementResponse placement = placementService.getFinalPlacement();
        return ResponseEntity.ok(placement);
    }

    // GET /api/placement/tree
    @GetMapping("tree")
    public ResponseEntity<TreePlacementResponse> getTreePlacement() {
        TreePlacementResponse placement = placementService.getTreePlacement();
        return ResponseEntity.ok(placement);
    }
}
