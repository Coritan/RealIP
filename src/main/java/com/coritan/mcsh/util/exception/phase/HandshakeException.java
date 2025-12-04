package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

/**
 * An exception thrown during the handshake handling of MCSH
 */
public class HandshakeException extends MCSHException {

	public HandshakeException(Throwable throwable) {
		super("An exception occured during the handshake process", throwable);
	}


	public HandshakeException(String message) {
		super(message);
	}


	public HandshakeException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public HandshakeException() {
		super();
	}

}
