package com.ccsw.tutorial.exception;

public class DosClientesDistintosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DosClientesDistintosException(String message) {
		super(message);
	}
}