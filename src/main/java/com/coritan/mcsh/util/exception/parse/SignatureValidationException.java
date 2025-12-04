package com.coritan.mcsh.util.exception.parse;

import com.coritan.mcsh.util.exception.phase.HandshakeException;

/**
 * An exception thrown when a handshake packet failed signature validation
 */
public class SignatureValidationException extends HandshakeException {

	public SignatureValidationException() {
		super();
	}

}
