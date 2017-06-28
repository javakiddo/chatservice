package com.mycompany.chatservice.event;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class PresenceEventListener
{
	private ParticipantRepository participantRepository;
	
	private SimpMessagingTemplate messagingTemplate;
	
	private String loginDestination;
	
	private String logoutDestination;
	
	public PresenceEventListener(SimpMessagingTemplate messagingTemplate, ParticipantRepository participantRepository)
	{
		this.messagingTemplate = messagingTemplate;
		this.participantRepository = participantRepository;
	}
	
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event)
	{
		SimpMessageHeaderAccessor headers= SimpMessageHeaderAccessor.wrap(event.getMessage());
		String name = headers.getUser().getName();
		LoginEvent loginEvent= new LoginEvent(name);
		messagingTemplate.convertAndSend(loginDestination,loginEvent);
		participantRepository.add(headers.getSessionId(), loginEvent);		
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event)	
	{
		Optional.ofNullable(participantRepository.getParticipant(event.getSessionId())).ifPresent(loginEvent->{
			messagingTemplate.convertAndSend(logoutDestination, new LogoutEvent(loginEvent.getUsername()));
			participantRepository.removeParticipant(event.getSessionId());			
		});
	}
	
	public void setLoginDestination(String loginDestination) {
		this.loginDestination = loginDestination;
	}

	public void setLogoutDestination(String logoutDestination) {
		this.logoutDestination = logoutDestination;
	}
}
