package org.gameManager.Module;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.gameManager.Data.GameData;
import org.gameManager.Data.Info.SpawnInfo;
import org.gameManager.Enum.GameManagerMessage;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
        GameData.nowGame = GameManagerMessage.DEFAULT_SPAWN.getMessage();
        for (String gameName : Objects.requireNonNull(config.getConfigurationSection("")).getKeys(false)) {
            GameData.gameInfo.add(gameName);
            double x = config.getDouble(gameName + spawnX, 0);
            double y = config.getDouble(gameName + spawnY, 0);
            double z = config.getDouble(gameName + spawnZ, 0);
            GameData.gameSpawnInfo.put(gameName, new SpawnInfo(x, y, z));
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
            messageModule.sendPlayer(sender, GameManagerMessage.GAME_CREATE_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameStart(CommandSender sender, String[] data) {
        if (GameData.isGame) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_ALREADY_START.getMessage());
            return;
        }
        GameData.isGame = true;
        String gameName = data[0];
        if (GameData.gameInfo.contains(gameName)) {
            if (!GameData.gameSpawnInfo.containsKey(gameName)) {
                messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS_SPAWN.getMessage());
                return;
            }
            GameData.nowGame = gameName;
            playerModule.getServerPlayers().forEach(player -> {
                messageModule.sendPlayer(player, GameManagerMessage.GAME_START_MESSAGE.getMessage());
            });
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
        SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(GameData.nowGame);
//        for (Map.Entry<UUID, Player> playerEntry : GameData.playerInfo.entrySet()) {
        playerModule.getServerPlayers().forEach(player -> {
            World world = player.getWorld();
            player.teleport(new Location(world, spawnInfo.getX(), spawnInfo.getY(), spawnInfo.getZ()));
            messageModule.sendPlayer(player, GameManagerMessage.GAME_STOP_MESSAGE.getMessage());
        });
    }

    public void gameDelete(CommandSender sender, String[] data) {
        String gameName = data[0];
        if (!GameData.gameInfo.contains(gameName)) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        } else {
            FileConfiguration config = configModule.getConfig(configFile);
            config.set(gameName, null);
            configModule.saveConfig(config, configFile);
            messageModule.sendPlayer(sender, GameManagerMessage.GAME_DELETE_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameSetSpawn(CommandSender sender, String[] data) {
        String gameName = data[0];
        if (!GameData.gameInfo.contains(gameName)) {
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_GAME_IS_NOT_EXITS.getMessage());
        } else {
            Player player = (Player) sender;
            Location location = player.getLocation();
            FileConfiguration config = configModule.getConfig(configFile);
            config.set(gameName + ".spawn.x", location.getX());
            config.set(gameName + ".spawn.y", location.getY());
            config.set(gameName + ".spawn.z", location.getZ());
            configModule.saveConfig(config, configFile);
            messageModule.sendPlayer(sender, GameManagerMessage.GAME_SET_SPAWN_MESSAGE.getMessage());
            reloadGameInfo();
        }
    }

    public void gameList(CommandSender sender, String[] data) {
        messageModule.sendPlayer(sender, GameManagerMessage.INFO_MESSAGE_GAME_LIST.getMessage());
        GameData.gameInfo.forEach(gameName -> {
            messageModule.sendPlayer(sender, ChatColor.AQUA + gameName);
        });
        messageModule.sendPlayer(sender, GameManagerMessage.INFO_MESSAGE_GAME_LIST.getMessage());
    }
}
