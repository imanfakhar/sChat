package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.chatserver.event.ClientConnectionClosedEvent;
import de.sChat.server.chatserver.event.ClientConnectionOpendEvent;
import de.sChat.server.chatserver.event.IncommingMessageEvent;
import de.sChat.server.chatserver.event.OutGoingMessageEvent;
import de.sChat.server.chatserver.message.Message;
import de.sChat.server.chatserver.message.MessageParser;

public class ChatServer {

	EventBus eventbus = new EventBus();
	private Vector<HandlerRunnable> listeMitThreads = new Vector<HandlerRunnable>();

	public ChatServer(Integer port) throws IOException, EventBusException {
		System.out.println(MessageParser.parseMessage(new Message("Test", "Hallo du!")));
		eventbus.subscribe(this);

		ServerSocket server = new ServerSocket(port.intValue());
		Thread thread = new Thread(new AcceptRunnable(server, eventbus));
		thread.start();
		System.out.println("Server started @ "+port);
		//createClient("",1234);
	}

	private void createClient(String address, int i) throws UnknownHostException, IOException {
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
