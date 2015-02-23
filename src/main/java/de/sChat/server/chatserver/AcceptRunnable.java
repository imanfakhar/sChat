package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptRunnable implements Runnable {

	private ServerSocket server;
	private ChatServer chatserver;


	public AcceptRunnable(ServerSocket server, ChatServer chatserver) {
		this.server = server;
		this.chatserver = chatserver;
	}

	@Override
	public void run() {
		while (Thread.currentThread().getState()!=Thread.State.TERMINATED) {
			Socket client;
			try {
				client = server.accept();
				HandlerRunnable newHandler = new HandlerRunnable(client, chatserver);
				chatserver.addClient(newHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
