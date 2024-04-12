package org.gameManager.Data;

import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.gameManager.Data.Info.SpawnInfo;

import java.util.*;

public class GameData {
    public static HashMap<String, SpawnInfo> gameSpawnInfo = new LinkedHashMap<>();
    public static List<String> gameInfo = new ArrayList<>();
    public static HashMap<UUID, Player> playerInfo = new LinkedHashMap<>();
    public static String nowGame = "default";
    public static Boolean isGame = false;
}
