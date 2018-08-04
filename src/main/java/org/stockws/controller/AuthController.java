package org.stockws.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@PostMapping("/me")
	public Principal me(Principal principal){
		return principal;
	}
	
}
