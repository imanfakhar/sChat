package de.sChat.server.data.chatClient;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.TextMessage;

public class ChatClientBusAdapter {
	
	private EntityManager manager;

	public ChatClientBusAdapter(EventBus bus, EntityManager manager) throws EventBusException {
		bus.subscribe(this);
		this.manager = manager;
	}
	
	@Handler
	public void handleIncommingMessageEvent(IncommingMessageEvent event)
	{
		DaoChatClient daocc = new DaoChatClient(manager);
		DaoTextMessage daotm = new DaoTextMessage(manager);
		List<ChatClient> list = daocc.getAllChatClients();
		for (ChatClient chatClient : list) {
			System.out.println("Vorher: "+daocc.getChatClient(chatClient.getName()).getMessages().size());
			TextMessage msg = (TextMessage) event.getMsg();
			msg.setOwner(chatClient);
			msg.setCreationTime(new Date());
			daotm.setTextMessage(msg);
			chatClient.getMessages().add(msg);
			daocc.setChatClient(chatClient);
			System.out.println("Nachher: "+daocc.getChatClient(chatClient.getName()).getMessages().size());
		}
	}

}
