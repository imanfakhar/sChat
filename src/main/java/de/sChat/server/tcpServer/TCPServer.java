package de.sChat.server.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.persistence.EntityManager;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.events.OutGoingMessageEvent;
import de.sChat.server.tcpServer.events.ClientConnectionClosedEvent;
import de.sChat.server.tcpServer.events.ClientConnectionOpendEvent;

public class TCPServer {

	private Vector<HandlerRunnable> listeMitThreads = new Vector<HandlerRunnable>();
	private EventBus eventbus;
	private EntityManager manager;

	public TCPServer(EventBus eventbus, EntityManager manager, Integer port) throws IOException, EventBusException {
		this.eventbus = eventbus;
		this.manager = manager;
		eventbus.subscribe(this);
		ServerSocket server = new ServerSocket(port.intValue());
		Thread thread = new Thread(new AcceptRunnable(server, eventbus, manager));
		thread.start();
		System.out.println("Server started @ "+port);
	}

	public void createClient(String address, int i) throws UnknownHostException, IOException {
		HandlerRunnable newHandler = new HandlerRunnable(new Socket(address, i), eventbus, manager);
		eventbus.publishSync(new ClientConnectionOpendEvent(newHandler));
	}
	
	@Handler
	public void incommingMessage(IncommingMessageEvent event) {
		eventbus.publishSync(new OutGoingMessageEvent());
	}

	@Handler
	public void addClient(ClientConnectionOpendEvent event) throws EventBusException {
		eventbus.subscribe(event.getHandlerRunnable());
		Thread thread = new Thread(event.getHandlerRunnable());
		listeMitThreads.add(event.getHandlerRunnable());
		thread.start();
	}

	@Handler
	public void removeClient(ClientConnectionClosedEvent event) {
		listeMitThreads.remove(event.getHandlerRunnable());
	}
}
