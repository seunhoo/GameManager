package org.gameManager.Module;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class PlayerModule {

    public List<Player> getServerPlayers(){
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
}
