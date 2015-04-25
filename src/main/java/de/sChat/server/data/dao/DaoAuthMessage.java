package de.sChat.server.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import de.sChat.server.data.messages.AuthMessage;

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
