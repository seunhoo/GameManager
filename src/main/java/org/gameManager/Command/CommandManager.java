package org.gameManager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.gameManager.Data.GameData;
import org.gameManager.Enum.GameManagerMessage;
import org.gameManager.GameManager;
import org.gameManager.Module.GameModule;
import org.gameManager.Module.MessageModule;

import java.util.EnumSet;
import java.util.Objects;


public class CommandManager implements CommandExecutor
{
	private final GameCommand gameCommand  = new GameCommand();
	private final MessageModule messageModule = new MessageModule();
	public CommandManager(GameManager gameManager)
	{
		for (GameManagerMessage gameManagerMessage : EnumSet.range(GameManagerMessage.COMMAND_CREATE, GameManagerMessage.COMMAND_NOW)) {
			Objects.requireNonNull(gameManager.getCommand(gameManagerMessage.getMessage())).setExecutor(this);
		}
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] data) {
		GameManagerMessage message = GameManagerMessage.getByMessage(label);
		switch (message){
			case COMMAND_CREATE -> gameCommand.gameCreate(sender,data);
			case COMMAND_START -> gameCommand.gameStart(sender,data);
			case COMMAND_STOP -> gameCommand.gameStop(sender,data);
			case COMMAND_DELETE -> gameCommand.gameDelete(sender,data);
			case COMMAND_SET_SPAWN -> gameCommand.gameSetSpawn(sender,data);
			case COMMAND_GAME_LIST -> gameCommand.gameList(sender,data);
			case COMMAND_HELP -> explainCommand(sender);
			case COMMAND_NOW -> nowCommand(sender);
		}
		return true;
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
