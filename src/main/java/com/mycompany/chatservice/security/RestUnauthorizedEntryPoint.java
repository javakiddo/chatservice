package com.mycompany.chatservice.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestUnauthorizedEntryPoint implements AuthenticationEntryPoint{

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {
        SecurityUtilsService.sendError(response, exception, HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication failed");
    }
	
}
