package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Player;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
        return playerRepository.save(player);
    }

    // Player aktualisieren
    public Player updatePlayer(Integer id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player nicht gefunden mit ID: " + id));

        player.setTeam(playerDetails.getTeam());
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
