package com.enjin.argentumcraft.ArgentumcraftPVP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ArgentumcraftPVP extends JavaPlugin{
	
	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("PLACEHOLDER")){
			return true;
		}
		return false;
	}
}
