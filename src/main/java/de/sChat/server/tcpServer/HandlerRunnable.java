package de.sChat.server.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.events.OutGoingMessageEvent;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;
import de.sChat.server.data.messages.parser.MessageParser;
import de.sChat.server.httpServer.EntityManagerHolder;
import de.sChat.server.tcpServer.events.ClientConnectionClosedEvent;

public class HandlerRunnable implements Runnable{

	private PrintWriter out;
	private Socket acceptedClient;
	private EventBus eventbus;

	private BufferedReader reader;
	private String name;
	private EntityManager em;

	public HandlerRunnable(Socket acceptedClient, EventBus eventbus, EntityManager em) throws IOException 
	{
		this.acceptedClient = acceptedClient;
		this.eventbus = eventbus;
		this.em = em;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);
	}

	@Handler
	public void outgoingMessage(OutGoingMessageEvent event) 
	{
		DaoChatClient dao = new DaoChatClient(em);
		DaoTextMessage daotm = new DaoTextMessage(em);
		ChatClient cc = dao.getChatClient(name);
		List<TextMessage> messages = daotm.getMessagesSince(cc, new Date());
    	for (TextMessage textMessage : messages) {
    		out.println(MessageParser.parseMessage(textMessage));
		}
	}

	public void run() {
		try {
			while (!out.checkError()) {
				reader = new BufferedReader(new InputStreamReader(acceptedClient.getInputStream()));
				handleConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (acceptedClient != null) {
				try {
					acceptedClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void handleConnection() throws IOException {
		String input = null;
		while (reader.ready()) {
			input = reader.readLine();
		}
		if (input != null) 
		{
			Message msg = MessageParser.parseMessage(input);
			this.name = ((TextMessage) msg).getName();
			eventbus.publishSync(new IncommingMessageEvent(msg));
		}
		if(acceptedClient.isClosed())
		{
			eventbus.publishSync(new ClientConnectionClosedEvent(this));
		}
	}
}