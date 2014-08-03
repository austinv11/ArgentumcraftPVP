package com.enjin.argentumcraft.ArgentumcraftPVP;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.argentumcraft.PVPSession.KillStreakHandler;
import com.enjin.argentumcraft.PVPSession.PVPSession;

public class ArgentumcraftPVP extends JavaPlugin{
	
	// Is the game running?
	public boolean isGameInSession = false;
	
	// Is the count down before the game running?
	public boolean countDownInProgress = false;
	
	// Seconds left on the countdown
	public int countdown = 20;
	
	// TaskID of countdown
	public int taskID;
	
	// Number of kills the blue team has
	public static int blueTeamKills = 0;
	
	// Number of kills the red team has
	public static int redTeamKills = 0;
	
	// The number of kills needed to win. Set in the /aPVPStart command
	public static int killsToWin = 0;
	
	// Stores the names of players on the blue team
	static HashMap<String, Integer> blueTeam = new HashMap<String, Integer>();
	
	// Stores the names of players on the red team
	static HashMap<String, Integer> redTeam = new HashMap<String, Integer>();
	
	// Used to determine if the given string is numeric
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
	
	// Determines whether the given player has already joined the game
	public static boolean isInGame(String player)
	{
		if (redTeam.containsKey(player) || blueTeam.containsKey(player))
		{
			return true;
		}
		return false;
	}
	
	// Determines what team the given player is on
	public static String getTeam(String player)
	{
		if (redTeam.containsKey(player))
		{
			return "red";
		}
		return "blue";
	}
	
	// Adds given player to given team
	public static void addPlayer(String player, String team)
	{
		if (team == "blue")
		{
			blueTeam.put(player, 0);
		}
		else if (team == "red")
		{
			redTeam.put(player, 0);
		}
	}
	
	// Removes given player from his team
	public static void removePlayer(String player)
	{
		if (getTeam(player) == "red")
		{
			redTeam.remove(player);
		}
		else
		{
			blueTeam.remove(player);
		}
	}
	
	/*public static boolean teamHasWon()
	{
		if (redTeamKills == )
		return false;
	}*/
	
	@Override
	public void onEnable(){
		new Resources(this, getLogger(), getDataFolder(), getConfig());
		getLogger().info("Enabling ArgentumcraftPVP");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Disabling ArgentumcraftPVP");
		Resources.closeDatabase();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("aPVPStart")){
			// Check for the correct arguments
			if (args.length == 1)
			{
				// Check if the given argument is numeric
				if (isNumeric(args[0]))
				{
					// Check if the game is already running
					if (!isGameInSession)
					{
						killsToWin = Integer.parseInt(args[0]);
						isGameInSession = true;
						Bukkit.broadcastMessage("Beginning game of PVP...");
						Bukkit.broadcastMessage("Type /join <team> to join!");
						
						taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Resources.INSTANCE, new Runnable()
						{
							@Override
							public void run()
							{
								if (countdown == 0)
								{
									Bukkit.getServer().getScheduler().cancelTask(taskID);
									Bukkit.broadcastMessage("Game starting!");
								}
								else
								{
									Bukkit.broadcastMessage("Game starting in "+countdown);
									countdown--;
								}
							}
						}, 0L, 20L);
						return true;
					}
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
		else if (cmd.getName().equalsIgnoreCase("join"))
		{
			if (sender instanceof Player)
			{
				Player player = (Player) sender;
				if (args.length == 1)
				{
					if (args[0].toLowerCase() == "red")
					{
						addPlayer(player.getName(),"red");
						player.sendMessage("You have joined the red team!");
					}
					else if(args[0].toLowerCase() == "blue")
					{
						addPlayer(player.getName(), "blue");
						player.sendMessage("You have joined the blue team!");
					}
					else
					{
						player.sendMessage("That is not a valid team!");
					}
				}
			}
			else
			{
				sender.sendMessage("Silly console! Joining is for players!");
			}
		}else if (cmd.getName().equalsIgnoreCase("aPVPSetSpawn")){
			if (args.length > 0){
				if (args[0].toLowerCase() == "red" || args[0].toLowerCase() == "blue"){
					if (sender instanceof Player){
						Player player = (Player) sender;
						Location loc = player.getLocation().clone();
						Resources.DATABASE.set(args[0].toLowerCase()+".SPAWN_POINT", loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getWorld().getName());
						Resources.saveDatabase();
						sender.sendMessage("Set the spawn of the "+args[0].toLowerCase()+" team");
					}else{
						sender.sendMessage("Sorry, you can't use this command");
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else if (cmd.getName().equalsIgnoreCase("killstreak")){
			if (sender instanceof Player){
				if (isInGame(sender.getName())){
					if (args.length > 0){
						new KillStreakHandler((Player) sender, args);
					}else{
						new KillStreakHandler((Player) sender);
					}
				}else{
					sender.sendMessage("Sorry, you need to be in game to use that awesomeness");
				}
			}else{
				sender.sendMessage("Bad console!");
			}
			return true;
		}
		return false;
	}
}
