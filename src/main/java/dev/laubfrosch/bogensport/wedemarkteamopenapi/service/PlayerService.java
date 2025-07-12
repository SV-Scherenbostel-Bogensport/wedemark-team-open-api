package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Player;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.PlayerRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    final PlayerRepository playerRepository;
    final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    // Alle Player laden
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Player nach ID finden
    public Optional<Player> getPlayerById(Integer id) {
        return playerRepository.findById(id);
    }

    // Anzahl aller Player
    public CountDto getTotalPlayerCount() {
        return playerRepository.countPlayers();
    }

    // Neuen Player anlegen
    public Player createPlayer(Player player) {
        if (player.getTeamId() != null) {
            if (!teamRepository.existsById(player.getTeamId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team mit ID " + player.getTeamId() + " existiert nicht");
            }
        }

        return playerRepository.save(player);
    }

    // Player aktualisieren
    public Player updatePlayer(Integer id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player nicht gefunden mit ID: " + id));

        if (playerDetails.getTeamId() != null) {
            if (!teamRepository.existsById(playerDetails.getTeamId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team mit ID " + playerDetails.getTeamId() + " existiert nicht");
            }
        }

        player.setTeamId(playerDetails.getTeamId());
        player.setSquadNumber(playerDetails.getSquadNumber());
        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());

        return playerRepository.save(player);
    }

    // Player löschen
    public boolean deletePlayer(Integer id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
