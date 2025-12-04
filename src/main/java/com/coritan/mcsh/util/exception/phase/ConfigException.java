package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

/**
 * An exception thrown during the config loading, reloeading, saving, etc. process of MCSH
 */
public class ConfigException extends MCSHException {

	public ConfigException(Throwable throwable) {
		super("An exception occured during the config process", throwable);
	}


	public ConfigException(String message) {
		super(message);
	}


	public ConfigException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public ConfigException() {
		super();
	}

}
