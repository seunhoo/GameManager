package org.gameManager.Data;

import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.gameManager.Data.Info.SpawnInfo;

import java.util.*;

public class GameData {
    public static HashMap<String, SpawnInfo> gameSpawnInfo = new LinkedHashMap<>();
    public static List<String> gameInfo = new ArrayList<>();
    public static HashMap<HashMap<String,String>,SpawnInfo> playerSpawnInfo = new LinkedHashMap<>();
    public static String nowGame = "";
    public static Boolean isGame = false;
}
