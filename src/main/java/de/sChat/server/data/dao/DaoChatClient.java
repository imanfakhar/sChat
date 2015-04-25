package de.sChat.server.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import de.sChat.server.data.chatClient.ChatClient;

public class DaoChatClient {
	

	private EntityManager entityManager;


	public DaoChatClient(EntityManager entityManager) 
	{
		this.entityManager = entityManager;
	}
	
	public ChatClient getChatClient(String name)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		ChatClient chatclient = (ChatClient) entityManager.find(ChatClient.class, name);
		transaction.commit();
		if(chatclient == null)
			chatclient = new ChatClient(name);
		return chatclient;
	}
	
	public List<ChatClient> getAllChatClients()
	{
		TypedQuery<ChatClient> query = entityManager.createQuery("FROM ChatClient", ChatClient.class);
		List<ChatClient> list = (List<ChatClient>) query.getResultList();
		return list;
	}
	
	public void setChatClient(ChatClient cc)
	{
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(cc);
		transaction.commit();
	}
}
