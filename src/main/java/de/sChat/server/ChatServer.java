package de.sChat.server;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.sChat.server.data.chatClient.ChatClientBusAdapter;
import de.sChat.server.httpServer.HttpServer;
import de.sChat.server.tcpServer.TCPServer;

public class ChatServer {
	
	private EventBus bus;
	private HttpServer http;
	private TCPServer tcp;
	private ChatClientBusAdapter busadapter;
	private EntityManagerFactory emf;

	public ChatServer(Integer tcpport, Integer httpport) throws IOException, EventBusException 
	{
		emf = Persistence.createEntityManagerFactory("emf");
		bus = new EventBus();
		busadapter = new ChatClientBusAdapter(bus,emf);
		this.tcp = new TCPServer(bus, emf, tcpport);
		this.http = new HttpServer(bus, emf , httpport);
	}

}
