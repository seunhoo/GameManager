package org.gameManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.gameManager.Command.CommandManager;
import org.gameManager.Event.EventManager;
import org.gameManager.Module.GameModule;

import java.util.logging.Logger;

public final class GameManager extends JavaPlugin {
    public static Plugin plugin;
    public static Logger log = Bukkit.getLogger();
    public static Plugin getPlugin(){
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        CommandManager commandManager = new CommandManager(this);
        new EventManager().settingEvent(this.getServer(),this);
        new GameModule().reloadGameInfo();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
