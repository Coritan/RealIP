package com.coritan.mcsh.bukkit.paper;

import com.coritan.mcsh.bukkit.MCSHBukkit;
import com.coritan.mcsh.bukkit.paper.handler.PaperHandshakeHandler;
import com.coritan.mcsh.bukkit.provider.BukkitImplProvider;

/**
 * Bukkit provider for the PaperSpigot implementation of MCSH
 */
public class BukkitPaper extends BukkitImplProvider {

	public BukkitPaper(MCSHBukkit bukkitPlugin) {
		super(bukkitPlugin);
	}

	@Override
	public void load() {
		PaperHandshakeHandler packetHandler = new PaperHandshakeHandler(this);
		getPlugin().getServer().getPluginManager().registerEvents(packetHandler, getPlugin());
	}

}
