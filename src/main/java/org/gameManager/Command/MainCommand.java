package org.gameManager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.units.qual.C;
import org.gameManager.Data.GameData;
import org.gameManager.Enum.GameManagerMessage;
import org.gameManager.Module.MessageModule;

import java.util.EnumSet;

public class MainCommand {
    private final GameCommand gameCommand = new GameCommand();
    private final MessageModule messageModule = new MessageModule();
    public void gameCommandHandler(CommandSender sender, Command command, String label, String[] data)
    {
        GameManagerMessage message = GameManagerMessage.getByMessage(label);
        switch (message){
            case COMMAND_CREATE -> gameCommand.gameCreate(sender,data);
            case COMMAND_START -> gameCommand.gameStart(sender,data);
            case COMMAND_STOP -> gameCommand.gameStop(sender,data);
            case COMMAND_DELETE -> gameCommand.gameDelete(sender,data);
            case COMMAND_SET_SPAWN -> gameCommand.gameSetSpawn(sender,data);
            case COMMAND_GAME_LIST -> gameCommand.gameList(sender,data);
            case COMMAND_EXPLAIN -> explainCommand(sender);
            case COMMAND_NOW -> nowCommand(sender);
        }
    }
    private void explainCommand(CommandSender sender){
        for (GameManagerMessage gameManagerMessage : EnumSet.range(GameManagerMessage.INFO_COMMAND_EXPLAIN1, GameManagerMessage.INFO_COMMAND_EXPLAIN17)) {
            messageModule.sendPlayerNoPrefix(sender,gameManagerMessage.getMessage());
        }
    }
    private void nowCommand(CommandSender sender){
        messageModule.sendPlayerNoPrefix(sender,GameManagerMessage.INFO_MESSAGE_GAME_NOW.getMessage());
        messageModule.sendPlayerNoPrefix(sender, GameData.nowGame);
    }
}
