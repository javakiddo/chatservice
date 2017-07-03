package com.mycompany.chatservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mycompany.chatservice.domain.User;
import com.mycompany.chatservice.repository.UserRepository;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{
	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
	
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		log.debug("Authenticating {}", username);
		User user = userRepository.findByLogin(username);
		
		if (user == null)
		{
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
			
		}
		else if (!user.getEnabled())
		{
			throw new UserNotEnabledException("User " + username + " was not enabled");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		user.getAuthorities().stream().forEach(authority -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
			grantedAuthorities.add(grantedAuthority);
		});
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
		
	}
	
}
