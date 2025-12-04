package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

/**
 * An exception thrown during reflection operations for MCSH
 */
public class ReflectionException extends MCSHException {

	public ReflectionException(Throwable throwable) {
		super("An exception occured during the reflection process", throwable);
	}


	public ReflectionException(String message) {
		super(message);
	}


	public ReflectionException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public ReflectionException() {
		super();
	}

}
