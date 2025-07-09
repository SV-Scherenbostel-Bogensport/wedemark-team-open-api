package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamCountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
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
    public List<AttendeesDto> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    // Team nach ID finden
    public Optional<Team> getTeamById(Integer id) {
        return teamRepository.findById(id);
    }

    // Ids aller Teams ermitteln
    public List<GetTeamIdsDto> getTeamIds() {
        return teamRepository.getAllTeamIds();
    }

    // Anzahl an Teams
    public TeamCountDto getTotalTeamCount() {
        return teamRepository.getTotalTeamCount();
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
    public boolean deleteTeam(Integer id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}