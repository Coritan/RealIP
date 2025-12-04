package com.coritan.mcsh.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.coritan.mcsh.MCSHPacketHandler;
import com.coritan.mcsh.MCSHPlugin;
import com.coritan.mcsh.geyser.GeyserUtils;
import com.coritan.mcsh.provider.ConfigProvider;
import com.coritan.mcsh.util.Debugger;
import com.coritan.mcsh.util.exception.phase.InitializationException;
import com.coritan.mcsh.velocity.handler.VelocityHandshakeHandler;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The entry point for Velocity servers
 */
@Plugin(
	id = "mcsh",
	name = "MCSH",
	description = "MCSH IP parsing capabilities for Velocity"
)
public class MCSHVelocity implements MCSHPlugin {

	private final ProxyServer server;
	private final Logger logger;
	private final Path dataFolder;

	private ConfigProvider configProvider;
	private MCSHPacketHandler packetHandler;
	private Debugger debugger;

	@Inject
	public MCSHVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataFolder) {
		this.server = server;
		this.logger = logger;
		this.dataFolder = dataFolder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent e) {
		try {
			configProvider = new VelocityConfig(dataFolder.toFile(), this);
			debugger = Debugger.createDebugger(this);
			packetHandler = new MCSHPacketHandler(this);

			server.getEventManager().register(this, new VelocityHandshakeHandler(this));

			GeyserUtils.initGeyser(this, configProvider);

			initialization();
		} catch (Exception exception) {
			throw new InitializationException(exception);
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
	public Logger getLogger() {
		return logger;
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
