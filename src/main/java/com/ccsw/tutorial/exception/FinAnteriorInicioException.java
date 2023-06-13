package com.ccsw.tutorial.exception;

public class FinAnteriorInicioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FinAnteriorInicioException(String message) {
		super(message);
	}
}