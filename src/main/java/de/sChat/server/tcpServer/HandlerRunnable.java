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
import de.sChat.server.data.messages.RegisterMessage;
import de.sChat.server.data.messages.ServerConnectMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;
import de.sChat.server.data.messages.parser.MessageParser;
import de.sChat.server.httpServer.BusHolder;
import de.sChat.server.httpServer.EntityManagerHolder;
import de.sChat.server.tcpServer.events.ClientConnectionClosedEvent;

public class HandlerRunnable implements Runnable{

	private PrintWriter out;
	private Socket acceptedClient;
	private EventBus eventbus;

	private BufferedReader reader;
	private EntityManager em;
	private String last = "";

	public HandlerRunnable(Socket acceptedClient, EventBus eventbus, EntityManager em) throws IOException 
	{
		this.acceptedClient = acceptedClient;
		this.eventbus = eventbus;
		this.em = em;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);
	}

	@Handler
	public void outgoingMessage(IncommingMessageEvent event) 
	{
		Message msg = event.getMsg();
		if(msg.getUid() == null)
			msg.setUid("");
		if(!msg.getUid().equals("Server"))
		{
			out.println(MessageParser.parseMessage(msg));
		}
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(acceptedClient.getInputStream()));
			while (!out.checkError()) {
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

	private boolean processRegisterMessage(Message message) {
		DaoChatClient daocc = new DaoChatClient(EntityManagerHolder.getEntityManager());
		RegisterMessage msg = (RegisterMessage) message;
		ChatClient cl = daocc.getChatClient(msg.getUsername());
		if(cl.getPassword() == null)
		{
			cl.setPassword(msg.getPassword());
			daocc.setChatClient(cl);
			return true;
		}
		return false;
	}

	private void handleConnection() throws IOException {
		String input = null;
		input = reader.readLine();
		if (input != null) 
		{
			System.out.println("<--- "+input);
			Message msg = MessageParser.parseMessage(input);
			System.out.println("<--- "+MessageParser.parseMessage(msg));
			if(msg instanceof TextMessage)
			{
				msg.setUid("Server");
				BusHolder.getBus().publishSync(new IncommingMessageEvent(msg));
			}
			if(msg instanceof RegisterMessage)
			{
				processRegisterMessage(msg);
			}
			if(msg instanceof ServerConnectMessage)
			{
				DaoTextMessage daotm = new DaoTextMessage(em);
				Integer time = ((ServerConnectMessage) msg).getTime();
				Date date = new Date(time*1000L);
				System.out.println(date.toGMTString());
				List<TextMessage> messages = daotm.getMessagesSince(date);
				for (TextMessage textMessage : messages) {
					if(!last.equals(textMessage.getMessage()))
					{
						out.println(MessageParser.parseMessage(textMessage));
						System.out.println("---> "+MessageParser.parseMessage(textMessage));
						last = textMessage.getMessage();
					}
				}
			}
		}
		if(acceptedClient.isClosed())
		{
			eventbus.publishSync(new ClientConnectionClosedEvent(this));
		}
	}
}