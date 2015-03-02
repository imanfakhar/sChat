package de.sChat.server.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.shared.events.IncommingMessageEvent;
import de.sChat.server.shared.events.OutGoingMessageEvent;
import de.sChat.server.shared.messageHub.HubMessage;
import de.sChat.server.shared.messageHub.MessageHubWrapper;
import de.sChat.server.shared.messages.Message;
import de.sChat.server.shared.messages.MessageParser;
import de.sChat.server.tcpServer.events.ClientConnectionClosedEvent;

public class HandlerRunnable implements Runnable{

	private PrintWriter out;
	private Socket acceptedClient;
	private EventBus eventbus;

	private BufferedReader reader;
	private long lastTimeStamp = 0;

	public HandlerRunnable(Socket acceptedClient, EventBus eventbus) throws IOException 
	{
		this.acceptedClient = acceptedClient;
		this.eventbus = eventbus;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);
	}

	@Handler
	public void outgoingMessage(OutGoingMessageEvent event) 
	{
		ArrayList<HubMessage> list = MessageHubWrapper.getHub().getMessages(lastTimeStamp);
		if(list.size() > 0)
		{
			lastTimeStamp = list.get(0).getTimestamp();
			for (HubMessage hubMessage : list) {
				Message msg = hubMessage.getMsg();
				if(!(msg.getSender() == this))
					out.println(MessageParser.parseMessage(msg));
			}
		}
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
			Message msg = MessageParser.parseMessage(input);
			msg.setSender(this);
			eventbus.publishSync(new IncommingMessageEvent(msg));
		}
		if(acceptedClient.isClosed())
		{
			eventbus.publishSync(new ClientConnectionClosedEvent(this));
		}
	}
}
