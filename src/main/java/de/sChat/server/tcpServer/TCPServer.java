package de.sChat.server.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;

import javax.persistence.EntityManager;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.bus.EventBusException;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.events.OutGoingMessageEvent;
import de.sChat.server.data.messages.ServerConnectMessage;
import de.sChat.server.data.messages.TextMessage;
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

	public void connect(String address, int i) throws UnknownHostException, IOException {
		HandlerRunnable newHandler = new HandlerRunnable(new Socket(address, i), eventbus, manager);
		eventbus.publishSync(new ClientConnectionOpendEvent(newHandler));
		DaoTextMessage dao = new DaoTextMessage(manager);
		TextMessage msg = dao.getLastMessages();
		int time = 0;
		System.out.println("###########################");
		if(msg != null)
		{
			time = (int) (msg.getCreationTime().getTime()/1000);
			System.out.println(new Date(time*1000L).toGMTString());
		}
		eventbus.publishAsync(new IncommingMessageEvent(new ServerConnectMessage(time)));
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
