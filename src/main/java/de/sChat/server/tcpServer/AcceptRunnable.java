package de.sChat.server.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.sChat.server.tcpServer.events.ClientConnectionOpendEvent;

public class AcceptRunnable implements Runnable {

	private ServerSocket server;
	private EventBus bus;
	private EntityManager manager;


	public AcceptRunnable(ServerSocket server, EventBus bus, EntityManager manager) {
		this.bus = bus;
		this.server = server;
		this.manager = manager;
	}

	@Override
	public void run() {
		while (Thread.currentThread().getState()!=Thread.State.TERMINATED) {
			Socket client;
			try {
				client = server.accept();
				HandlerRunnable newHandler = new HandlerRunnable(client, bus, manager);
				bus.publishSync(new ClientConnectionOpendEvent(newHandler));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
