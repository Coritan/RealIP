package com.coritan.mcsh.velocity;

import com.coritan.mcsh.provider.ConfigProvider;

import java.io.File;

/**
 * Velocity's configuration implementation
 */
public class VelocityConfig extends ConfigProvider {

	public VelocityConfig(File dataFolder) {
		this.dataFolder = dataFolder;
	}

}
