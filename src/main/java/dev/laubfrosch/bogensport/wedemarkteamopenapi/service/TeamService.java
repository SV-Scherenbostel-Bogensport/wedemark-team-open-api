package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // Alle Teams laden
    public List<TeamDto> getAllTeams() {
        return teamRepository.getAllTeams();
        //return teamRepository.findAll();
    }

    // Team nach ID finden
    public Optional<Team> getTeamById(Integer id) {
        return teamRepository.findById(id);
    }

    // Neues Team erstellen
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Team aktualisieren
    public Team updateTeam(Integer id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team nicht gefunden mit ID: " + id));

        team.setName(teamDetails.getName());
        team.setContactEmail(teamDetails.getContactEmail());
        team.setPlayerCount(teamDetails.getPlayerCount());
        team.setHasPayed(teamDetails.getHasPayed());
        team.setLookingForTeammates(teamDetails.getLookingForTeammates());

        return teamRepository.save(team);
    }

    // Team löschen
    public void deleteTeam(Integer id) {
        teamRepository.deleteById(id);
    }

    // Teams die Mitspieler suchen
    // public List<Team> getTeamsLookingForTeammates() {
    //     return teamRepository.findByLookingForTeammatesTrue();
    // }

    // Bezahlte Teams
    // public List<Team> getPaidTeams() {
    //     return teamRepository.findByHasPayedTrue();
    // }

    // Teams nach Namen suchen
    // public List<Team> searchTeamsByName(String name) {
    //     return teamRepository.findByNameContainingIgnoreCase(name);
    // }
}