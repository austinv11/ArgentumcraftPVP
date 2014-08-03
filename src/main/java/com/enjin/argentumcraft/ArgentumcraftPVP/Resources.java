package com.enjin.argentumcraft.ArgentumcraftPVP;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Resources {
	public static ArgentumcraftPVP INSTANCE;
	public static Logger LOGGER;
	public static File DATA_FOLDER;
	public static FileConfiguration CONFIG;
	public static File DATA_FILE;
	public static FileConfiguration DATABASE;
	
	public Resources(ArgentumcraftPVP plugin, Logger logger, File dataFolder, FileConfiguration config){
		INSTANCE = plugin;
		LOGGER = logger;
		DATA_FOLDER = dataFolder;
		CONFIG = config;
		DATA_FILE = new File(DATA_FOLDER,"database.yml");
		DATABASE = YamlConfiguration.loadConfiguration(DATA_FILE);
		openDatabase();
		setupConfig();
	}
	
	//Initiating the config
	public static void setupConfig(){
		CONFIG.addDefault("KILLSTREAK_NAME", "KILLS");//TODO add more defaults
		INSTANCE.saveConfig();
	}
	
	
	//Logic to handle creating/opening the database
	public static void openDatabase(){
		DATABASE.createSection("blue");
		DATABASE.createSection("red");
		DATABASE.options().copyDefaults(true);
		try{
			DATABASE.save(DATA_FILE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Saving everything
	public static void saveDatabase(){
		assert DATA_FILE.exists() == true;
		DATABASE.options().copyDefaults(true);
		try{
			DATABASE.save(DATA_FILE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Logic to handle closing
	public static void closeDatabase(){
		DATABASE.set("red", null);
		DATABASE.set("blue", null);
		saveDatabase();
	}
}
