package com.coritan.mcsh.velocity;

import com.moandjiezana.toml.Toml;
import com.coritan.mcsh.MCSHPlugin;
import com.coritan.mcsh.provider.ConfigProvider;
import com.coritan.mcsh.util.exception.config.ConfigLoadException;
import com.coritan.mcsh.util.exception.config.ConfigReloadException;
import com.coritan.mcsh.util.exception.config.ConfigResetException;
import com.coritan.mcsh.util.exception.phase.ConfigException;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Velocity's configuration implementation
 */
public class VelocityConfig extends ConfigProvider {

	private final MCSHPlugin plugin;

	private Toml loadedToml;

	public VelocityConfig(File dataFolder, MCSHPlugin plugin) throws ConfigException {
		this.plugin = plugin;
		this.dataFolder = dataFolder;
		this.configFile = new File(this.dataFolder, "config.toml");

		try {
			reload();
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	@Override
	protected void checkNodes(String... nodes) {
		for (String node : nodes) {
			if (!loadedToml.contains(node))
				throw new ConfigException("The node \"" + node + "\" does not exist in the config.");
		}
	}

	@Override
	protected void reset() throws ConfigResetException {
		try {
			loadedToml = null;  // To the garbage collector you go

			try {
				configFile.delete();
			} catch (Exception ignored) {
				// Just ignore since it either does not exist, or we can overwrite
			}

			try (InputStream in = VelocityConfig.class.getResourceAsStream("/config.toml")) {
				Files.copy(in, configFile.toPath());
			}
		} catch (Exception e) {
			throw new ConfigResetException(e);
		}
	}

	@Override
	protected void load() throws ConfigLoadException {
		try {
			loadedToml = new Toml().read(configFile);

			checkNodes("only-allow-proxy-connections", "timestamp-validation", "debug-mode", "enable-geyser-support", "pre-login-event");

			this.onlyProxy = loadedToml.getBoolean("only-allow-proxy-connections");
			this.timestampValidationMode = loadedToml.getString("timestamp-validation");
			this.doDebug = loadedToml.getBoolean("debug-mode");
			this.geyser = loadedToml.getBoolean("enable-geyser-support");
			this.velocityHandlePreLoginEvent = loadedToml.getBoolean("pre-login-event");
		} catch (Exception e) {
			throw new ConfigLoadException(e);
		}
	}

	@Override
	public void reload() throws ConfigReloadException {
		try {
			if (!dataFolder.exists())
				dataFolder.mkdir();

			if (!configFile.exists())
				reset();

			try {
				load();
			} catch (ConfigLoadException exception) {
				plugin.getLogger().warning("Config loading failed, resetting to default config. (This can be ignored if you just switched builds of MCSH)");
				reset();
				reload(); // Redo cycle, possible StackOverFlow, but realistically only happens if reset fails
			}
		} catch (Exception e) {
			throw new ConfigReloadException(e);
		}
	}

}
