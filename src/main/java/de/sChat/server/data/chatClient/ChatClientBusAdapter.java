package de.sChat.server.data.chatClient;

import java.util.List;

import javax.persistence.EntityManager;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.dao.DaoAuthMessage;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.LoginMessage;
import de.sChat.server.data.messages.RegisterMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;

public class ChatClientBusAdapter {
	
	private EntityManager manager;

	public ChatClientBusAdapter(EventBus bus, EntityManager manager) throws EventBusException {
		bus.subscribe(this);
		this.manager = manager;
	}
	
	@Handler
	public void handleIncommingMessageEvent(IncommingMessageEvent event)
	{
		Message message = event.getMsg();
		if(message instanceof TextMessage)
			processTextMessage(message);
	}

	private void processTextMessage(Message message) 
	{
		DaoChatClient daocc = new DaoChatClient(manager);
		DaoTextMessage daotm = new DaoTextMessage(manager);
		DaoAuthMessage daoa = new DaoAuthMessage(manager);
		List<ChatClient> list = daocc.getAllChatClients();
		String uid = message.getUid();
		TextMessage msg = (TextMessage) message;
		if(!daoa.getAuthMessage(uid).getOwner().getName().equals(msg.getName()))
			return;
		for (ChatClient chatClient : list) {
			TextMessage tmsg = new TextMessage(msg);
			tmsg.setOwner(chatClient);
			daotm.setTextMessage(tmsg);
			chatClient.getMessages().add(tmsg);
			daocc.setChatClient(chatClient);
		}
	}

}
