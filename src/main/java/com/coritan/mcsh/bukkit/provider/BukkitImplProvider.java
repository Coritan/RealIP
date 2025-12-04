package com.coritan.mcsh.bukkit.provider;

import com.coritan.mcsh.bukkit.MCSHBukkit;

/**
 * A provider for Bukkit implementations of MCSH
 */
public abstract class BukkitImplProvider {

	private final MCSHBukkit bukkitPlugin;

	public BukkitImplProvider(MCSHBukkit bukkitPlugin) {
		this.bukkitPlugin = bukkitPlugin;
	}


	public MCSHBukkit getPlugin() {
		return bukkitPlugin;
	}

	/**
	 * Loads the Bukkit provider
	 */
	public abstract void load();


	/**
	 * Checks for the Paper handshake event class for the Paper implementation
	 * @return Boolean stating if the runtime has the proper Paper event
	 */
	public static boolean hasPaperEvent() {
		try {
			Class.forName("com.destroystokyo.paper.event.player.PlayerHandshakeEvent");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
