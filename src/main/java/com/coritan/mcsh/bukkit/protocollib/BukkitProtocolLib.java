package com.coritan.mcsh.bukkit.protocollib;

import com.coritan.mcsh.bukkit.MCSHBukkit;
import com.coritan.mcsh.bukkit.protocollib.handler.ProtocolLibHandshakeHandler;
import com.coritan.mcsh.bukkit.provider.BukkitImplProvider;

/**
 * Bukkit provider for the ProtocolLib implementation of MCSH
 */
public class BukkitProtocolLib extends BukkitImplProvider {

	public BukkitProtocolLib(MCSHBukkit bukkitPlugin) {
		super(bukkitPlugin);
	}

	@Override
	public void load() {
		ProtocolLibHandshakeHandler packetHandler = new ProtocolLibHandshakeHandler(this);
		com.comphenix.protocol.ProtocolLibrary.getProtocolManager().addPacketListener(packetHandler);
	}

}
