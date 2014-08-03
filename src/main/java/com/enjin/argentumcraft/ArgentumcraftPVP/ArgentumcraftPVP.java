package com.enjin.argentumcraft.ArgentumcraftPVP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.argentumcraft.PVPSession.PVPSession;

public class ArgentumcraftPVP extends JavaPlugin{
	
	// Is the game running?
	public boolean isGameInSession = false;
	
	// Number of kills the blue team has
	public int blueTeamKills = 0;
	
	// Number of kills the red team has
	public int redTeamKills = 0;
	
	// The number of kills needed to win. Set in the /aPVPStart command
	public int killsToWin = 0;
	
	
	public static boolean isNumeric(String string)
	{
		try
		{
			int i = Integer.parseInt(string);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public void onEnable(){
		new Resources(this, getLogger(), getDataFolder(), getConfig());
		getLogger().info("Enabling ArgentumcraftPVP");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Disabling ArgentumcraftPVP");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("aPVPStart")){
			if (args.length == 1)
			{
				if (isNumeric(args[0]))
				{
					sender.sendMessage("Beginning game...");
					killsToWin = Integer.parseInt(args[0]);
					isGameInSession = true;
					//Initiating the game (object)
					new PVPSession();
					return true;
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("aPVPStop")){
			sender.sendMessage("Stopping game...");
			isGameInSession = false;
			//Closing the game
			PVPSession.closeGame();
			return true;
		}
		return false;
	}
}
