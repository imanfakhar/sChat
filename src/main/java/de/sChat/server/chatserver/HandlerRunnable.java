package de.sChat.server.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.chatserver.event.ClientConnectionClosedEvent;
import de.sChat.server.chatserver.event.IncommingMessageEvent;
import de.sChat.server.chatserver.event.OutGoingMessageEvent;
import de.sChat.server.chatserver.message.Message;
import de.sChat.server.chatserver.message.MessageParser;

public class HandlerRunnable implements Runnable {

	private PrintWriter out;
	private Socket acceptedClient;
	private EventBus eventbus;

	private String name = null;
	private BufferedReader reader;

	public HandlerRunnable(Socket acceptedClient, EventBus eventbus) throws IOException 
	{
		this.acceptedClient = acceptedClient;
		this.eventbus = eventbus;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);
	}

	@Handler
	public void outgoingMessage(OutGoingMessageEvent event) 
	{
		Message msg = event.getMsg();
		if(!msg.getName().equals(name))
			out.println(MessageParser.parseMessage(event.getMsg()));
	}

	public void run() {
		try {
			while (!out.checkError()) {
				reader = new BufferedReader(new InputStreamReader(acceptedClient.getInputStream()));
				handleConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (acceptedClient != null) {
				try {
					acceptedClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void handleConnection() throws IOException {
		String input = null;
		while (reader.ready()) {
			input = reader.readLine();
		}
		if (input != null) 
		{
			eventbus.publishSync(new IncommingMessageEvent(MessageParser.parseMessage(input)));
		}
		if(acceptedClient.isClosed())
		{
			eventbus.publishSync(new ClientConnectionClosedEvent(this));
		}
	}
}
