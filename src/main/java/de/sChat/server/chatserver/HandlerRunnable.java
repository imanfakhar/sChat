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

public class HandlerRunnable implements Runnable {
	private PrintWriter out;
	private Socket acceptedClient;
	private EventBus bus;
	private String name = null;
	private BufferedReader reader;

	public HandlerRunnable(Socket acceptedClient, EventBus bus) throws IOException {
		this.acceptedClient = acceptedClient;
		this.bus = bus;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);

	}

	@Handler
	public void outgoingMessage(OutGoingMessageEvent event) {
		System.out.println("out hash of:[" + event.getMsg().getName() + "]: " + out.toString()
				+ " " + event.getMsg().getNachricht());
		if (event.getMsg().getName() != null && !event.getMsg().getName().equals(this.name)) {
			out.print(getEscapedName());
			out.println(event.getMsg().getName() + ":" + event.getMsg().getNachricht());
			printPrompt();
		}
		if (event.getMsg().getName().equals(this.name)) {
			printPrompt();
		}
	}

	private String getEscapedName() {
		String esc = "";
		for (int i = 0; i < getPrompt().length(); i++) {
			esc += "\b";
		}
		return esc;
	}

	public void run() {
		try {
			out.println("Bitte geben Sie ihren Nutzername an");
			reader = new BufferedReader(new InputStreamReader(
					acceptedClient.getInputStream()));
			this.name = reader.readLine();
			out.println("Vielen Dank, " + name + "!");
			printPrompt();
			bus.publishSync(new IncommingMessageEvent(new Message("ROBOT", ">>>>>>>>>>>>>>>>[" + name + "] has entered the Room")));
			System.out.println(this.toString() + " heiﬂt jetzt : [" + name
					+ "]");
			while (!out.checkError()) {
				reader = new BufferedReader(new InputStreamReader(
						acceptedClient.getInputStream()));
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
		bus.publishSync(new IncommingMessageEvent(new Message("ROBOT", "<<<<<<<<<<<<<<<<[" + name
				+ "] has left the Room")));
	}

	private void printPrompt() {
		out.print(getPrompt());
	}

	private String getPrompt() {
		return name + ":";
	}

	private void handleConnection() throws IOException {
		String input = null;
		while (reader.ready() && !acceptedClient.isClosed()) {
			input = reader.readLine();
		}
		if (input != null) {
			bus.publishSync(new IncommingMessageEvent(new Message(name, input)));
		}
		if(acceptedClient.isClosed())
		{
			bus.publishSync(new ClientConnectionClosedEvent(this));
		}
	}
}