package com.javachinna.service;

import com.javachinna.model.Session;

import java.util.List;

public interface SessionService{
	List<Session> getAllSessions();
	Session saveSession(Session session);
	Session findSessionById(Long id);

	
}
