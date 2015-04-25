package de.sChat.server;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.sChat.server.data.chatClient.ChatClientBusAdapter;
import de.sChat.server.httpServer.HttpServer;
import de.sChat.server.tcpServer.TCPServer;

public class ChatServer {
	
	private EventBus bus;
	private TCPServer tcp;
	private EntityManagerFactory emf;
	private Integer tcpport;
	private Integer httpport;

	public ChatServer(Integer tcpport, Integer httpport) throws IOException, EventBusException 
	{
		emf = Persistence.createEntityManagerFactory("emf");
		bus = new EventBus();
		new ChatClientBusAdapter(bus,emf.createEntityManager());
		this.tcpport = tcpport;
		this.httpport = httpport;
	}

	public void starthttp() {
		new HttpServer(bus, emf.createEntityManager(), httpport);
	}

	public void starttcp() throws IOException,
			EventBusException {
		this.tcp = new TCPServer(bus, emf.createEntityManager(), tcpport);
	}

	public void connect(String serveraddress, int serverport) throws UnknownHostException, IOException {
		this.tcp.connect(serveraddress, serverport);
	}


}