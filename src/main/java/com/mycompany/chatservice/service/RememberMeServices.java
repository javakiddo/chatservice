package com.mycompany.chatservice.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import com.mycompany.chatservice.config.WebSecurityConfig;
import com.mycompany.chatservice.domain.Token;
import com.mycompany.chatservice.repository.TokenRepository;

/**
 * Custom implementation of Spring Security's RememberMeServices.
 * <p/>
 * Persistent tokens are used by Spring Security to automatically log in users.
 * <p/>
 * This is a specific implementation of Spring Security's remember-me authentication, but it is much
 * more powerful than the standard implementations:
 * <ul>
 * <li>It allows a user to see the list of his currently opened sessions, and invalidate them</li>
 * <li>It stores more information, such as the IP address and the user agent, for audit purposes<li>
 * <li>When a user logs out, only his current session is invalidated, and not all of his sessions</li>
 * </ul>
 * <p/>
 */
public class RememberMeServices extends AbstractRememberMeServices
{
	
	private final Logger log = LoggerFactory.getLogger(RememberMeServices.class);
	
	// Token is valid for one month
	private static final int TOKEN_VALIDITY_DAYS = 31;
	
	private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;
	
	private static final int DEFAULT_SERIES_LENGTH = 16;
	
	private static final int DEFAULT_TOKEN_LENGTH = 16;
	
	private SecureRandom random;
	
	private TokenRepository tokenRepository;
	
	public RememberMeServices(Environment env, org.springframework.security.core.userdetails.UserDetailsService userDetailsService)
	{
		
		super(WebSecurityConfig.REMEMBER_ME_KEY, userDetailsService);
		super.setParameter("rememberme");
		random = new SecureRandom();
	}
	
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication)
	{
		
	}
	
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException
	{
		
		return null;
	}
	
	/**
	 * Validate the token and return it.
	 */
	private Token getPersistentToken(String[] cookieTokens)
	{
		
		if (cookieTokens.length != 2)
		{
			throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}
		
		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];
		
		Token token = null;
		
		try
		{
			token = tokenRepository.findOne(presentedSeries);
		}
		catch (DataAccessException e)
		{
			log.error("Error to access database", e);
		}
		if (token == null)
		{
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
		}
		
		// We have a match for this user/series combination
		log.info("presentedToken={} / tokenValue={}", presentedToken, token.getValue());
		
		if (!presentedToken.equals(token.getValue()))
		{
			// Token doesn't match series value. Delete this session and throw an exception.
			throw new CookieTheftException("Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack.");
		}
		if (DateUtils.addDays(token.getDate(), TOKEN_VALIDITY_DAYS).before(new Date()))
		{
			tokenRepository.delete(token.getSeries());
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}
		
		return token;
	}
	
	private String generateSeriesData()
	{
		byte[] newSeries = new byte[DEFAULT_SERIES_LENGTH];
		random.nextBytes(newSeries);
		return new String(Base64.encode(newSeries));
	}
	
	private String generateTokenData()
	{
		byte[] newToken = new byte[DEFAULT_TOKEN_LENGTH];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}
}
