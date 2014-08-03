package com.enjin.argentumcraft.PVPSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

public class KillStreakHandler {
	Player player;
	List<String> args;
	
	public KillStreakHandler(Player p, String[] a){
		player = p;
		args = new ArrayList<String>(Arrays.asList(a));
		//Do kill streak based on args, if args[0] = list, then list all killstreaks in red (unless available, then green)
	}
	
	public KillStreakHandler(Player p){
		player = p;
		//Do best killstreak available
	}
}
