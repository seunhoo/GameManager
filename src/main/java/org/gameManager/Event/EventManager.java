package org.gameManager.Event;


import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class EventManager {
    public void settingEvent(Server server , Plugin plugin){
        server.getPluginManager().registerEvents(new PlayerEvent(), plugin);
    }
}
