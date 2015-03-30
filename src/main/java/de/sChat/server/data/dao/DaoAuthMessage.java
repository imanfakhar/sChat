package de.sChat.server.data.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.TextMessage;

public class DaoAuthMessage {


	private EntityManager entityManager;


	public DaoAuthMessage(EntityManager entityManager) 
	{
		this.entityManager = entityManager;
	}

	public AuthMessage getAuthMessage(String uid)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		AuthMessage msg = entityManager.find(AuthMessage.class, uid);
		transaction.commit();
		return msg;
	}

	public void setAuthMessage(AuthMessage cc)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(cc);
		transaction.commit();
	}
}
