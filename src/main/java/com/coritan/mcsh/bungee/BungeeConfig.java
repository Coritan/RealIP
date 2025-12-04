package com.coritan.mcsh.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import com.coritan.mcsh.provider.ConfigProvider;

/**
 * Bungee's configuration implementation
 */
public class BungeeConfig extends ConfigProvider {

	public BungeeConfig(Plugin plugin) {
		this.dataFolder = plugin.getDataFolder();
	}

}
