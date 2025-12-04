package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

public class InvalidSecretException extends MCSHException {

	public InvalidSecretException(Throwable throwable) {
		super("An invalid secret was provided during the geyser handshake process", throwable);
	}


	public InvalidSecretException(String message) {
		super(message);
	}


	public InvalidSecretException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public InvalidSecretException() {
		super();
	}

}
