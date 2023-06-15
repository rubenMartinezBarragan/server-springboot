package com.ccsw.tutorial.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ccsw.tutorial.exception.ApplicationError;
import com.ccsw.tutorial.exception.ClienteNoExisteException;
import com.ccsw.tutorial.exception.DosClientesDistintosException;
import com.ccsw.tutorial.exception.FinAnteriorInicioException;
import com.ccsw.tutorial.exception.PeriodoPrestamoMaximoException;
import com.ccsw.tutorial.exception.PrestadosDosJuegosException;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(FinAnteriorInicioException.class)
	public ResponseEntity<ApplicationError> handlerCustomerUnauthorizedException(FinAnteriorInicioException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(401);
		error.setMessage(exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(PeriodoPrestamoMaximoException.class)
	public ResponseEntity<ApplicationError> handlerCustomerForbiddenException(PeriodoPrestamoMaximoException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(403);
		error.setMessage(exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(DosClientesDistintosException.class)
	public ResponseEntity<ApplicationError> handlerCustomerNotFoundException(DosClientesDistintosException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(404);
		error.setMessage(exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ClienteNoExisteException.class)
	public ResponseEntity<ApplicationError> handlerCustomerClienteNoExisteException(ClienteNoExisteException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(405);
		error.setMessage(exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(PrestadosDosJuegosException.class)
	public ResponseEntity<ApplicationError> handlerCustomerConflictException(PrestadosDosJuegosException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(409);
		error.setMessage(exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
}