package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.PlayersByTeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Player;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.TeamService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // GET /api/teams - alle Teams abrufen ohne E-Mail → Teilnehmerliste
    @GetMapping
    public ResponseEntity<List<AttendeesDto>> getAllTeams() {
        List<AttendeesDto> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // GET /api/teams/{id} - Team nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/teams/count - Anzahl aller Teams abrufen
    @GetMapping("/count")
    public ResponseEntity<CountDto> getTotalTeamCount() {
        CountDto count = teamService.getTotalTeamCount();
        return ResponseEntity.ok(count);
    }

    // GET /api/teams/ids - Id und Name aller Teams abrufen
    @GetMapping("/ids")
    public ResponseEntity<List<GetTeamIdsDto>> getTeamIds() {
        List<GetTeamIdsDto> teams = teamService.getTeamIds();
        return ResponseEntity.ok(teams);
    }

    // POST /api/teams - neues Team erstellen
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    // PUT /api/teams/{id} - Team aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Integer id, @RequestBody Team teamDetails) {
        try {
            Team updatedTeam = teamService.updateTeam(id, teamDetails);
            return ResponseEntity.ok(updatedTeam);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/teams/{id} - Team löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        if(teamService.deleteTeam(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/teams/{id}/players
    @GetMapping("/{id}/players")
    public ResponseEntity<PlayersByTeamDto> getPlayersByTeam(@PathVariable @Min(1) Integer id) {
        PlayersByTeamDto playersDto = teamService.getPlayersByTeam(id);
        return ResponseEntity.ok(playersDto);
    }

}