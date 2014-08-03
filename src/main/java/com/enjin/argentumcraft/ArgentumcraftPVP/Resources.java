package com.enjin.argentumcraft.ArgentumcraftPVP;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Resources {
	public static ArgentumcraftPVP INSTANCE;
	public static Logger LOGGER;
	public static File DATA_FOLDER;
	public static FileConfiguration CONFIG;
	
	public Resources(ArgentumcraftPVP plugin, Logger logger, File dataFolder, FileConfiguration config){
		INSTANCE = plugin;
		LOGGER = logger;
		DATA_FOLDER = dataFolder;
		CONFIG = config;
	}
}
