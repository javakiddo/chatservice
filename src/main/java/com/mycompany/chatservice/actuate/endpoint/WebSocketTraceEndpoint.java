package com.mycompany.chatservice.actuate.endpoint;

import java.util.List;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.util.Assert;

public class WebSocketTraceEndpoint extends AbstractEndpoint<List<Trace>>
{
	private final TraceRepository repository;
	public WebSocketTraceEndpoint(TraceRepository repository)
	{
		
		super("websockettrace");
		Assert.notNull(repository, "Repository must not be null");
		this.repository = repository;
	}



	@Override
	public List<Trace> invoke()
	{
		return this.repository.findAll();
	}
}
