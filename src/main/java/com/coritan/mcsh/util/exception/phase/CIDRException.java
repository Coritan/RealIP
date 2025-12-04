package com.coritan.mcsh.util.exception.phase;

import com.coritan.mcsh.util.exception.MCSHException;

/**
 * An exception thrown during the CIDR process of MCSH
 */
public class CIDRException extends MCSHException {

	public CIDRException(Throwable throwable) {
		super("An exception occured during the CIDR process", throwable);
	}


	public CIDRException(String message) {
		super(message);
	}


	public CIDRException(String message, Throwable throwable) {
		super(message, throwable);
	}


	public CIDRException() {
		super();
	}

}
