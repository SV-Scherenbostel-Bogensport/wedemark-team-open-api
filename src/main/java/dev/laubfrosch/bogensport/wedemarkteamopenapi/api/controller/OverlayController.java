package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamNameResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.OverlayService;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/overlays")
public class OverlayController {

    private final OverlayService overlayService;

    public OverlayController(OverlayService overlayService, StatusService statusService) {
        this.overlayService = overlayService;
    }

    // GET /api/overlays/target/{targetCode}/teamname - ruft den jeweiligen Teamnamen ab, der derzeit auf der entsprechenden Scheibe schießt
    @GetMapping("/target/{targetCode}/teamname")
    public ResponseEntity<TeamNameResponse> getTeamNameByTargetCode(@PathVariable("targetCode") String targetCode) {
        TeamNameResponse teamNameResponse = overlayService.getTeamNameByTargetCode(targetCode);
        return ResponseEntity.ok(teamNameResponse);
    }

    // GET /api/overlays/target/{targetCode}/code - ruft den jeweiligen Status der entsprechenden Scheibe ab
    @GetMapping("/target/{targetCode}/status")
    public ResponseEntity<Status> getStatusByTargetCode(@PathVariable("targetCode") String targetCode) {
        Status status = overlayService.getStatusByTargetCode(targetCode);
        return ResponseEntity.ok(status);
    }
}
