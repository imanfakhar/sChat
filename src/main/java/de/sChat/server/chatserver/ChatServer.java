package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;

public class ChatServer {
	
	EventBus eventbus = new EventBus();
	private Vector<HandlerRunnable> listeMitThreads = new Vector<HandlerRunnable>();
	
	public ChatServer(Integer port) throws IOException {
		ServerSocket server = new ServerSocket(port.intValue());
		Thread thread = new Thread(new AcceptRunnable(server, this));
		thread.start();
		System.out.println("Server started @ "+port);
	}

	protected synchronized void incomingMessage(String name, String input) {
		for (HandlerRunnable handlerThread : listeMitThreads) {
			handlerThread.outgoingMessage(name, input);
		}
	}

	public synchronized void addClient(de.sChat.server.chatserver.HandlerRunnable newHandler) {
		Thread thread = new Thread(newHandler);
		listeMitThreads.add(newHandler);
		thread.start();
	}

}
