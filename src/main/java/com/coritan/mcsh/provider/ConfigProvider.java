package com.coritan.mcsh.provider;

import java.io.File;

/**
 * A provider for MCSH's hardcoded options.
 */
public abstract class ConfigProvider {

	protected File dataFolder;

	public boolean isOnlyProxy() {
		return false;
	}

	public boolean isGeyser() {
		return true;
	}

	public boolean handlePreLoginEvent() {
		return true;
	}

	public String getTimestampValidationMode() {
		return "system";
	}

	public boolean doDebug() {
		return false;
	}

	public boolean preferProtocolLib() {
		return false;
	}

	public File getDataFolder() {
		return dataFolder;
	}

	protected final long maxTimestampDifference = 3;

	public long getMaxTimestampDifference() {
		return maxTimestampDifference;
	}

}
