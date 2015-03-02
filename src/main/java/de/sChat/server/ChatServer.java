package de.sChat.server;

import java.io.IOException;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.sChat.server.httpServer.HttpServer;
import de.sChat.server.tcpServer.TCPServer;

public class ChatServer {
	
	private EventBus bus;
	private HttpServer http;
	private TCPServer tcp;

	public ChatServer(Integer tcpport, Integer httpport) throws IOException, EventBusException {
		bus = new EventBus();
		this.tcp = new TCPServer(bus, tcpport);
		this.http = new HttpServer(bus, httpport);
	}

}
