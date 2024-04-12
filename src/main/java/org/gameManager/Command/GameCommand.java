package org.gameManager.Command;

import org.bukkit.command.CommandSender;
import org.checkerframework.checker.units.qual.C;
import org.gameManager.Data.GameData;
import org.gameManager.Enum.GameManagerMessage;
import org.gameManager.Module.ConfigModule;
import org.gameManager.Module.GameModule;
import org.gameManager.Module.MessageModule;

public class GameCommand {
    private final GameModule gameModule =new GameModule();
    private final MessageModule messageModule = new MessageModule();
    public void gameCreate(CommandSender sender, String[] data){
        if(data.length == 1){
            gameModule.gameCreate(sender,data);
        }else{
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_WRONG_COMMAND.getMessage());
        }
    }
    public void gameStart(CommandSender sender, String[] data){
        if(data.length == 1){
            gameModule.gameStart(sender,data);
        }else{
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_WRONG_COMMAND.getMessage());
        }
    }
    public void gameStop(CommandSender sender, String[] data){
        if(GameData.isGame)
            gameModule.gameStop(sender,data);
        else
            messageModule.sendPlayer(sender,GameManagerMessage.ERROR_GAME_IS_NOT_START.getMessage());
    }
    public void gameDelete(CommandSender sender, String[] data){
        if(data.length == 1){
            gameModule.gameDelete(sender,data);
        }else{
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_WRONG_COMMAND.getMessage());
        }
    }
    public void gameSetSpawn(CommandSender sender, String[] data){
        if(data.length == 1){
            gameModule.gameSetSpawn(sender,data);
        }else{
            messageModule.sendPlayer(sender, GameManagerMessage.ERROR_WRONG_COMMAND.getMessage());
        }
    }
    public void gameList(CommandSender sender, String[] data){
        gameModule.gameList(sender,data);
    }
}
