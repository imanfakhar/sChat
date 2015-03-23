package de.sChat.server.data.chatClient;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.TextMessage;

public class ChatClientBusAdapter {
	
	private EntityManager em;

	public ChatClientBusAdapter(EventBus bus, EntityManagerFactory emf) throws EventBusException {
		bus.subscribe(this);
		em = emf.createEntityManager();
	}
	
	@Handler
	public void handleIncommingMessageEvent(IncommingMessageEvent event)
	{
		DaoChatClient dao = new DaoChatClient(em);
		List<ChatClient> list = dao.getAllChatClients();
		for (ChatClient chatClient : list) {
			chatClient.getMessages().add((TextMessage) event.getMsg());
			dao.setChatClient(chatClient);
		}
	}

}
