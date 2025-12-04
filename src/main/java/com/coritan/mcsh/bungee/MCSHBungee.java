package com.coritan.mcsh.bungee;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import com.coritan.mcsh.MCSHPacketHandler;
import com.coritan.mcsh.MCSHPlugin;
import com.coritan.mcsh.bungee.handler.BungeeHandshakeHandler;
import com.coritan.mcsh.geyser.GeyserUtils;
import com.coritan.mcsh.provider.ConfigProvider;
import com.coritan.mcsh.util.Debugger;
import com.coritan.mcsh.util.exception.phase.InitializationException;

/**
 * The entry point for Bungee servers
 */
public class MCSHBungee extends Plugin implements MCSHPlugin, Listener {

	private ConfigProvider configProvider;
	private MCSHPacketHandler packetHandler;
	private Debugger debugger;

	@Override
	public void onEnable() {
		try {
			configProvider = new BungeeConfig(this);
			debugger = Debugger.createDebugger(this);
			packetHandler = new MCSHPacketHandler(this);

			PluginManager pluginManager = this.getProxy().getPluginManager();
			pluginManager.registerListener(this, new BungeeHandshakeHandler(this));

			GeyserUtils.initGeyser(this, configProvider);

			initialization();
		} catch (Exception e) {
			throw new InitializationException(e);
		}
	}

	/*
	 * The provider's base methods
	 */

	@Override
	public ConfigProvider getConfigProvider() {
		return configProvider;
	}

	@Override
	public MCSHPacketHandler getPacketHandler() {
		return packetHandler;
	}

	@Override
	public Debugger getDebugger() {
		return debugger;
	}

}
