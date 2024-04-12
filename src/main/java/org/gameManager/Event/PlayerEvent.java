package org.gameManager.Event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.gameManager.Data.GameData;
import org.gameManager.Data.Info.SpawnInfo;

public class PlayerEvent implements Listener {

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event){
        if(GameData.isGame){
            Player player = event.getPlayer();
            SpawnInfo spawnInfo = GameData.gameSpawnInfo.get(GameData.nowGame);
            player.teleport(new Location(player.getWorld(),spawnInfo.getX(),spawnInfo.getY(),spawnInfo.getZ()));
        }
    }
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){

    }
}
