package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.FinalPlacementDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.QualificationPlacementDto;
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
    public ResponseEntity<List<QualificationPlacementDto>> getQualificationPlacement() {
        List<QualificationPlacementDto> placement = placementService.getQualificationPlacement();
        return ResponseEntity.ok(placement);
    }

    // GET /api/placement/final
    @GetMapping("final")
    public ResponseEntity<List<FinalPlacementDto>> getFinalPlacement() {
        List<FinalPlacementDto> placement = placementService.getFinalPlacement();
        return ResponseEntity.ok(placement);
    }
}
