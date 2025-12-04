package com.coritan.mcsh.bukkit;

import com.coritan.mcsh.provider.ConfigProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit's configuration implementation
 */
public class BukkitConfig extends ConfigProvider {

	public BukkitConfig(JavaPlugin plugin) {
		this.dataFolder = plugin.getDataFolder();
	}

}
