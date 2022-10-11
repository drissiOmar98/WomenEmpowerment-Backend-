package com.javachinna.service;

import com.javachinna.model.Session;
import com.javachinna.repo.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService{
	
	
	private SessionRepository sessionRepository;

	public SessionServiceImpl(SessionRepository sessionRepository) {
		super();
		this.sessionRepository = sessionRepository;
	}

	@Override
	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}

	@Override
	public Session saveSession(Session session) {
		return sessionRepository.save(session);
	}

	@Override
	public Session findSessionById(Long id) {
		return sessionRepository.findByid(id);
	}

	
}
