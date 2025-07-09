package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.TeamService;
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

    // GET /api/teams - alle Teams abrufen ohne E-Mail
    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // GET /api/teams/{id} - Team nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalTeamCount() {
        long count = teamService.getTotalTeamCount();
        return ResponseEntity.ok(count);
    }

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
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/teams/looking-for-teammates - Teams die Mitspieler suchen
    // @GetMapping("/looking-for-teammates")
    // public ResponseEntity<List<Team>> getTeamsLookingForTeammates() {
    //    List<Team> teams = teamService.getTeamsLookingForTeammates();
    //    return ResponseEntity.ok(teams);
    //}

    // GET /api/teams/paid - Bezahlte Teams
    // @GetMapping("/paid")
    // public ResponseEntity<List<Team>> getPaidTeams() {
    //     List<Team> teams = teamService.getPaidTeams();
    //     return ResponseEntity.ok(teams);
    // }

    // GET /api/teams/search?name={name} - Teams nach Namen suchen
    // @GetMapping("/search")
    // public ResponseEntity<List<Team>> searchTeams(@RequestParam String name) {
    //     List<Team> teams = teamService.searchTeamsByName(name);
    //     return ResponseEntity.ok(teams);
    // }
}