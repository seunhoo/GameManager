package org.gameManager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.gameManager.Enum.GameManagerMessage;
import org.gameManager.GameManager;

import java.util.EnumSet;
import java.util.Objects;


public class CommandManager implements CommandExecutor
{
	private final MainCommand mainCommand = new MainCommand();
	public CommandManager(GameManager gameManager)
	{
		for (GameManagerMessage gameManagerMessage : EnumSet.range(GameManagerMessage.COMMAND_CREATE, GameManagerMessage.COMMAND_NOW)) {
			Objects.requireNonNull(gameManager.getCommand(gameManagerMessage.getMessage())).setExecutor(this);
		}
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] data) {
		mainCommand.gameCommandHandler(sender,command,label,data);
		return true;
	}
}
