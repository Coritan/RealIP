package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

/**
 * An exception thrown during the initialization phase of MCSH
 */
public class InitializationException extends MCSHException {

	public InitializationException(Throwable throwable) {
		super("An exception occured during the initalization process", throwable);
	}


	public InitializationException(String message) {
		super(message);
	}


	public InitializationException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public InitializationException() {
		super();
	}

}