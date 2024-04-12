package org.gameManager.Module;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.gameManager.GameManager;

import java.io.File;
import java.io.IOException;

public class ConfigModule {
    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(GameManager.getPlugin().getDataFolder(), fileName);
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            GameManager.getPlugin().saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(FileConfiguration config, String fileName) {
        File configFile = new File(GameManager.getPlugin().getDataFolder(), fileName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }
}
