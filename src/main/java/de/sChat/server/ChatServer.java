package de.sChat.server;

import java.io.IOException;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.sChat.server.httpServer.HttpServer;
import de.sChat.server.shared.messageHub.MessageHub;
import de.sChat.server.shared.messageHub.MessageHubWrapper;
import de.sChat.server.tcpServer.TCPServer;

public class ChatServer {
	
	private EventBus bus;
	private HttpServer http;
	private TCPServer tcp;
	private MessageHub hub;

	public ChatServer(Integer tcpport, Integer httpport) throws IOException, EventBusException {
		bus = new EventBus();
		hub = new MessageHub(bus);
		bus.subscribe(hub);
		MessageHubWrapper.setHub(hub);
		this.tcp = new TCPServer(bus, tcpport);
		this.http = new HttpServer(httpport);
	}

}
