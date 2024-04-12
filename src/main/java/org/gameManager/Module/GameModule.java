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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GameModule {
    private final ConfigModule configModule = new ConfigModule();
    private final MessageModule messageModule = new MessageModule();
    private final PlayerModule playerModule = new PlayerModule();
    private final String configFile = "config.yml";

    private final String spawnX = ".spawn.x";
    private final String spawnY = ".spawn.y";
    private final String spawnZ = ".spawn.z";

    public void reloadGameInfo() {
        FileConfiguration config = configModule.getConfig(configFile);
        GameData.gameInfo.clear();
        GameData.nowGame = "";
        for (String gameName : Objects.requireNonNull(config.getConfigurationSection("")).getKeys(false)) {
            GameData.gameInfo.add(gameName);
            double x = config.getDouble(gameName + spawnX, 0);
            double y = config.getDouble(gameName + spawnY, 0);
            double z = config.getDouble(gameName + spawnZ, 0);
            GameData.gameSpawnInfo.put(gameName, new SpawnInfo(x, y, z));
            playerModule.getServerPlayers().forEach(player -> {
                String playerName = player.getName();
                double playerX = config.getDouble(gameName + "." + playerName + spawnX, 0);
                double playerY = config.getDouble(gameName + "." + playerName + spawnY, 0);
                double playerZ = config.getDouble(gameName + "." + playerName + spawnZ, 0);
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
            config.set(gameName + spawnX, 0);
            config.set(gameName + spawnY, 0);
            config.set(gameName + spawnZ, 0);
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
            SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(GameManagerMessage.DEFAULT_SPAWN.getMessage());
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
                config.set(gameName + "." + data[1] + spawnX, location.getX());
                config.set(gameName + "." + data[1] + spawnY, location.getY());
                config.set(gameName + "." + data[1] + spawnZ, location.getZ());
            } else {
                config.set(gameName + spawnX, location.getX());
                config.set(gameName + spawnY, location.getY());
                config.set(gameName + spawnZ, location.getZ());
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
