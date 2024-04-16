package org.gameManager.Module;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.gameManager.Data.GameData;
import org.gameManager.Data.Info.SpawnInfo;
import org.gameManager.Enum.GameManagerMessage;

import javax.swing.text.Style;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GameModule {
    private final ConfigModule configModule = new ConfigModule();
    private final MessageModule messageModule = new MessageModule();
    private final PlayerModule playerModule = new PlayerModule();
    private final String configFile = "config.yml";

    private final String spawn = ".spawn";
    private final String x = ".x";
    private final String y = ".y";
    private final String z = ".z";

    public void reloadGameInfo() {
        FileConfiguration config = configModule.getConfig(configFile);
        GameData.gameInfo.clear();
        GameData.nowGame = "";
        for (String gameName : Objects.requireNonNull(config.getConfigurationSection("")).getKeys(false)) {
            GameData.gameInfo.add(gameName);
            double X = config.getDouble(gameName + spawn + x, 0);
            double Y = config.getDouble(gameName + spawn + y, 0);
            double Z = config.getDouble(gameName + spawn + z, 0);
            GameData.gameSpawnInfo.put(gameName, new SpawnInfo(X,Y,Z));
            playerModule.getServerPlayers().forEach(player -> {
                String playerName = player.getName();
                double playerX = config.getDouble(gameName + "." + playerName + x, 0);
                double playerY = config.getDouble(gameName + "." + playerName + y, 0);
                double playerZ = config.getDouble(gameName + "." + playerName + z, 0);
                if (playerX != 0 && playerY != 0 && playerZ != 0) {
                    HashMap<String, String> map = new LinkedHashMap<>();
                    map.put(gameName, playerName);
                    GameData.playerSpawnInfo.put(map, new SpawnInfo(playerX, playerY, playerZ));
                }
            });
        }
    }

    public void gameCreate(CommandSender sender, String[] data) {
        String gameName = data[0];
        if (GameData.gameInfo.contains(gameName)) {
            messageModule.sendPlayer(sender);
        } else {
            FileConfiguration config = configModule.getConfig(configFile);
            config.set(gameName + spawn + x, 0);
            config.set(gameName + spawn + y, 0);
            config.set(gameName + spawn + z, 0);
            configModule.saveConfig(config, configFile);
            messageModule.sendPlayer(sender, ChatColor.WHITE + gameName + " | " + GameManagerMessage.GAME_CREATE_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameStart(CommandSender sender, String[] data) {
        if (GameData.isGame) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_ALREADY_START.getMessage());
            return;
        }
        String gameName = data[0];
        if (GameData.gameInfo.contains(gameName)) {
            if (!GameData.gameSpawnInfo.containsKey(gameName)) {
                messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS_SPAWN.getMessage());
                return;
            }
            SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(gameName);
            if (spawnInfo == null) {
                messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
            } else {
                playerModule.getServerPlayers().forEach(player -> {
                    HashMap<String,String> map = new LinkedHashMap<>();
                    map.put(gameName, player.getName());
                    Location location;
                    if(GameData.playerSpawnInfo.containsKey(map)){
                        SpawnInfo playerSpawnInfo = GameData.playerSpawnInfo.get(map);
                        location =  new Location(player.getWorld(), playerSpawnInfo.getX(), playerSpawnInfo.getY(), playerSpawnInfo.getZ());
                    }else{
                        location =  new Location(player.getWorld(), spawnInfo.getX(), spawnInfo.getY(), spawnInfo.getZ());
                    }
                    player.teleport(location);
                });
                GameData.nowGame = gameName;
                GameData.isGame = true;
                messageModule.broadcastMessage(ChatColor.WHITE + gameName + " | " + GameManagerMessage.GAME_START_MESSAGE.getMessage());
            }
        } else {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        }
    }

    public void gameStop(CommandSender sender, String[] data) {
        if (!GameData.isGame) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_START.getMessage());
            return;
        }
        GameData.isGame = false;
        SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(GameManagerMessage.DEFAULT_SPAWN.getMessage());
        if (spawnInfo == null) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        } else {
            playerModule.getServerPlayers().forEach(player -> {
                player.teleport(new Location(player.getWorld(), spawnInfo.getX(), spawnInfo.getY(), spawnInfo.getZ()));
                GameData.nowGame = "";
            });
            messageModule.broadcastMessage(ChatColor.WHITE + GameData.nowGame + " | " + GameManagerMessage.GAME_STOP_MESSAGE.getMessage());
        }
    }

    public void gameDelete(CommandSender sender, String[] data) {
        String gameName = data[0];
        if (!GameData.gameInfo.contains(gameName)) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        } else {
            FileConfiguration config = configModule.getConfig(configFile);
            config.set(gameName, null);
            configModule.saveConfig(config, configFile);
            messageModule.sendPlayer(sender, ChatColor.WHITE + gameName + " | " + GameManagerMessage.GAME_DELETE_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameSetSpawn(CommandSender sender, String[] data) {
        String gameName = data[0];
        if (!GameData.gameInfo.contains(gameName)) {
            messageModule.sendPlayer(sender, ChatColor.WHITE + gameName + " | " + GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        } else {
            Player player = (Player) sender;
            Location location = player.getLocation();
            FileConfiguration config = configModule.getConfig(configFile);
            if (data.length >= 2) {
                config.set(gameName + "." + data[1] + x, location.getX());
                config.set(gameName + "." + data[1] + y, location.getY());
                config.set(gameName + "." + data[1] + z, location.getZ());
            } else {
                config.set(gameName + spawn +  x, location.getX());
                config.set(gameName + spawn + y, location.getY());
                config.set(gameName + spawn + z, location.getZ());
            }
            configModule.saveConfig(config, configFile);
            messageModule.sendPlayer(sender, ChatColor.WHITE + gameName + " | " + GameManagerMessage.GAME_SET_SPAWN_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameList(CommandSender sender, String[] data) {
        messageModule.sendPlayerNoPrefix(sender, GameManagerMessage.INFO_MESSAGE_GAME_LIST.getMessage());
        GameData.gameInfo.forEach(gameName -> {
            messageModule.sendPlayerNoPrefix(sender, ChatColor.AQUA + gameName);
        });
    }
}
