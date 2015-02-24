package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.sChat.server.chatserver.event.ClientConnectionOpendEvent;

public class AcceptRunnable implements Runnable {

	private ServerSocket server;
	private EventBus bus;


	public AcceptRunnable(ServerSocket server, EventBus bus) {
		this.bus = bus;
		this.server = server;
	}

	@Override
	public void run() {
		while (Thread.currentThread().getState()!=Thread.State.TERMINATED) {
			Socket client;
			try {
				client = server.accept();
				HandlerRunnable newHandler = new HandlerRunnable(client, bus);
				bus.publishSync(new ClientConnectionOpendEvent(newHandler));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
