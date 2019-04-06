package org.stockws.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@GetMapping("/me")
	public Principal me(Principal principal){
		return principal;
	}
	
}
