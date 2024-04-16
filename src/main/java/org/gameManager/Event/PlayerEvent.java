package org.gameManager.Event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.gameManager.Data.GameData;
import org.gameManager.Data.Info.SpawnInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PlayerEvent implements Listener {

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event){
        if(GameData.isGame){
            Player player = event.getPlayer();
            SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(GameData.nowGame);
            HashMap<String,String> map = new LinkedHashMap<>();
            map.put(GameData.nowGame, player.getName());
            Location location;
            if(GameData.playerSpawnInfo.containsKey(map)){
                SpawnInfo playerSpawnInfo = GameData.playerSpawnInfo.get(map);
                location =  new Location(player.getWorld(), playerSpawnInfo.getX(), playerSpawnInfo.getY(), playerSpawnInfo.getZ());
            }else{
                location =  new Location(player.getWorld(), spawnInfo.getX(), spawnInfo.getY(), spawnInfo.getZ());
            }
            event.setRespawnLocation(location);
        }
    }
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
    }
}
