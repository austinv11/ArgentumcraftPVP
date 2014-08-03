package com.enjin.argentumcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.plugin.Plugin;

import com.enjin.argentumcraft.ArgentumcraftPVP.Resources;
import com.enjin.argentumcraft.PVPSession.DeathHandler;

public class ArgentumcraftPVPListener implements Listener
{
	Plugin plugin = (Plugin) Resources.INSTANCE;
	
	public ArgentumcraftPVPListener(){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if (event.getEntity() instanceof Player){//Is entity a player
			Player player = (Player) event.getEntity();
			if ((player.getHealth() - event.getDamage()) < 1){//Will the player die from the event
				//**USE THIS INSTEAD OF PLAYER DEATH EVENT**
				event.setCancelled(true);//Cancelling the event to prevent drops
				new DeathHandler(player, event.getDamager());//Handling the "death"
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent event){
		if (event.getEntity() instanceof Player){//Is entity a player
			Player player = (Player) event.getEntity();
			if ((player.getHealth() - event.getDamage()) < 1){//Will the player die from the event
				//**USE THIS INSTEAD OF PLAYER DEATH EVENT**
				event.setCancelled(true);//Cancelling the event to prevent drops
				new DeathHandler(player);//Handling the "death"
			}
		}
	}
	
	/*@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		**USE THE ENTITY DAMAGE EVENTS INSTEAD**
	}*/
}
