package com.mycompany.chatservice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.chatservice.domain.Token;
import com.mycompany.chatservice.domain.User;
import com.mycompany.chatservice.repository.TokenRepository;
import com.mycompany.chatservice.repository.UserRepository;
import com.mycompany.chatservice.security.SecurityUtilsService;

@RestController
//@Api(description = "Users management API")
public class SecurityController {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TokenRepository tokenRepo;

	@RequestMapping(value = "/security/account", method = RequestMethod.GET)
	public @ResponseBody User getUserAccount() {
		User user = userRepo.findByLogin(SecurityUtilsService.getCurrentLogin());
		user.setPassword(null);
		return user;
	}

	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/security/tokens", method = RequestMethod.GET)
	public @ResponseBody List<Token> getTokens() {
		List<Token> tokens = tokenRepo.findAll();
		for (Token t : tokens) {
			t.setSeries(null);
			t.setValue(null);
		}
		return tokens;
	}
}
