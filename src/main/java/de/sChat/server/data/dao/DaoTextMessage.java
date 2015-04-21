package de.sChat.server.data.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.messages.TextMessage;

public class DaoTextMessage {


	private EntityManager entityManager;


	public DaoTextMessage(EntityManager entityManager) 
	{
		this.entityManager = entityManager;
	}

	public TextMessage getTextMessage(String name)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		TextMessage TextMessage = entityManager.find(TextMessage.class, name);
		transaction.commit();
		return TextMessage;
	}

	public void setTextMessage(TextMessage cc)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(cc);
		transaction.commit();
	}

	public List<TextMessage> getMessagesSince(ChatClient client, Date time)
	{		
		TypedQuery<TextMessage> query = entityManager.createQuery("From TextMessage Where owner = :name And creationTime > :time",TextMessage.class);
		query.setParameter("name", client);
		query.setParameter("time", time);		
		return (List<TextMessage>) query.getResultList();
	}
	
	public List<TextMessage> getMessagesSince(Date time)
	{		
		TypedQuery<TextMessage> query = entityManager.createQuery("From TextMessage Where creationTime > :time",TextMessage.class);
		query.setParameter("time", time);		
		return (List<TextMessage>) query.getResultList();
	}
}
