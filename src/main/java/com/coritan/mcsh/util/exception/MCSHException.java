package com.coritan.mcsh.util.exception;

/**
 * Outline for MCSH exceptions
 */
public abstract class MCSHException extends RuntimeException {

	public MCSHException(Throwable throwable) {
		super(throwable);
	}


	public MCSHException(String message) {
		super(message);
	}


	public MCSHException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public MCSHException() {
		super();
	}

}
