package com.enjin.argentumcraft.PVPSession;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.enjin.argentumcraft.ArgentumcraftPVP.ArgentumcraftPVP;
import com.enjin.argentumcraft.ArgentumcraftPVP.Resources;

public class DeathHandler {
	private Player player;
	private Entity killer;
	
	public DeathHandler(Player p, Entity k){
		player = p;
		killer = k;
		clearInv();//Deleting the player inv
		//Handling killstreaks
		player.setLevel(0);
		Resources.DATABASE.set(ArgentumcraftPVP.getTeam(p.getName())+"."+p.getName()+"killstreak", 0);
		if (k instanceof Player){
			Player pKiller = (Player) k;
			pKiller.setLevel(pKiller.getLevel() + 1);
			Resources.DATABASE.set(ArgentumcraftPVP.getTeam(pKiller.getName())+"."+pKiller.getName()+"killstreak", pKiller.getLevel());
		}
		Resources.saveDatabase();
		teleport();
		if(k instanceof Player)
		{
			if(ArgentumcraftPVP.getTeam(((Player) k).getName()) == "blue")
			{
				ArgentumcraftPVP.blueTeamKills++;
			}
			else
			{
				ArgentumcraftPVP.redTeamKills++;
			}
		}
	}
	
	public DeathHandler(Player p){
		player = p;
		killer = null;
		clearInv();//Deleting the player inv
		//Handling killstreaks
		player.setLevel(0);
		Resources.DATABASE.set(ArgentumcraftPVP.getTeam(p.getName())+"."+p.getName()+"killstreak", 0);
		Resources.saveDatabase();
		teleport();
	}
	
	private void teleport(){
		String unParsedLoc = Resources.DATABASE.getString(ArgentumcraftPVP.getTeam(player.getName())+".SPAWN_POINT");
		String[] parsedLoc = unParsedLoc.split(",");
		int x = Integer.parseInt(parsedLoc[0]);
		int y = Integer.parseInt(parsedLoc[1]);
		int z = Integer.parseInt(parsedLoc[2]);
		String world = parsedLoc[3];
		player.teleport(new Location(Bukkit.getWorld(world), x, y, z));
	}
	
	private void clearInv(){
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
	}
}
