package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.chatserver.event.ClientConnectionClosedEvent;
import de.sChat.server.chatserver.event.ClientConnectionOpendEvent;
import de.sChat.server.chatserver.event.IncommingMessageEvent;
import de.sChat.server.chatserver.event.OutGoingMessageEvent;

public class ChatServer {
	
	EventBus eventbus = new EventBus();
	private Vector<HandlerRunnable> listeMitThreads = new Vector<HandlerRunnable>();
	
	public ChatServer(Integer port) throws IOException, EventBusException {
		eventbus.subscribe(this);
		ServerSocket server = new ServerSocket(port.intValue());
		Thread thread = new Thread(new AcceptRunnable(server, eventbus));
		thread.start();
		System.out.println("Server started @ "+port);
	}

	@Handler
	public void incomingMessage(IncommingMessageEvent event) {
		System.out.println(event.getMsg());
		eventbus.publishSync(new OutGoingMessageEvent(event.getMsg()));
	}
	
	@Handler
	public void clientLeft(ClientConnectionClosedEvent event) {
		listeMitThreads.remove(event.getHandlerRunnable());
	}
	
	@Handler
	public void addClient(ClientConnectionOpendEvent event) throws EventBusException {
		eventbus.subscribe(event.getHandlerRunnable());
		Thread thread = new Thread(event.getHandlerRunnable());
		listeMitThreads.add(event.getHandlerRunnable());
		thread.start();
	}
}
