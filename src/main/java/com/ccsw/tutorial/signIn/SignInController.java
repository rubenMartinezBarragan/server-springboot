package com.ccsw.tutorial.signIn;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ruben martinez barragan
 * 
 */
@Tag(name = "SignIn", description = "API of SignIn")
@RequestMapping(value = "/signIn")
@RestController
@CrossOrigin(origins = "*")
public class SignInController {
	/**
	 * Método para iniciar sesión
	 * 
	 */
	@Operation(summary = "Sign in", description = "Method that logs into the application")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String signIn() {
		return "Esta funcionalidad esta temporalmente inhabilitada.";
	}
}