package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.PlayersByTeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Player;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.PlayerRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
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
    public CountDto getTotalTeamCount() {
        return teamRepository.countTeams();
    }

    // Neues Team erstellen
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Team aktualisieren
    public Team updateTeam(Integer id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team mit ID " + id + " nicht gefunden"));

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

    // Alle Spieler eines Teams ausgeben
    public PlayersByTeamDto getPlayersByTeam(Integer id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team mit ID " + id + " nicht gefunden"));

        List<Player> players = playerRepository.findByTeam(team);

        return new PlayersByTeamDto(id, team.getName(), players);
    }
}