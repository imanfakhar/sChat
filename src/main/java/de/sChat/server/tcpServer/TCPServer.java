package de.sChat.server.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.shared.events.IncommingMessageEvent;
import de.sChat.server.shared.events.OutGoingMessageEvent;
import de.sChat.server.shared.messages.Message;
import de.sChat.server.shared.messages.MessageParser;
import de.sChat.server.tcpServer.events.ClientConnectionClosedEvent;
import de.sChat.server.tcpServer.events.ClientConnectionOpendEvent;

public class TCPServer {

	private Vector<ClientRunnable> listeMitThreads = new Vector<ClientRunnable>();
	private EventBus eventbus;

	public TCPServer(EventBus eventbus, Integer port) throws IOException, EventBusException {
		this.eventbus = eventbus;
		eventbus.subscribe(this);
		ServerSocket server = new ServerSocket(port.intValue());
		Thread thread = new Thread(new AcceptRunnable(server, eventbus));
		thread.start();
		System.out.println("Server started @ "+port);
	}

	public void createClient(String address, int i) throws UnknownHostException, IOException {
		HandlerRunnable newHandler = new HandlerRunnable(new Socket(address, i), eventbus);
		eventbus.publishSync(new ClientConnectionOpendEvent(newHandler));
	}
	
	@Handler
	public void incommingMessage(IncommingMessageEvent event) {
		eventbus.publishSync(new OutGoingMessageEvent(event.getMsg()));
	}

	@Handler
	public void addClient(ClientConnectionOpendEvent event) throws EventBusException {
		eventbus.subscribe(event.getHandlerRunnable());
		Thread thread = new Thread(event.getHandlerRunnable());
		listeMitThreads.add(event.getHandlerRunnable());
		thread.start();
	}

	@Handler
	public void addClient(ClientConnectionClosedEvent event) {
		listeMitThreads.remove(event.getHandlerRunnable());
	}
}
