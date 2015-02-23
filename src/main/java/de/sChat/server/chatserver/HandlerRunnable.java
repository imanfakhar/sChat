package de.sChat.server.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HandlerRunnable implements Runnable {
	private PrintWriter out;
	private Socket acceptedClient;
	private ChatServer server;
	private String name = null;
	private BufferedReader reader;

	public HandlerRunnable(Socket acceptedClient, ChatServer server) throws IOException {
		this.acceptedClient = acceptedClient;
		this.server = server;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);

	}

	public synchronized void outgoingMessage(String name, String input) {
		System.out.println("out hash of:[" + name + "]: " + out.toString()
				+ " " + input);
		if (name != null && !name.equals(this.name)) {
			out.print(getEscapedName());
			out.println(name + ":" + input);
			printPrompt();
		}
		if (name.equals(this.name)) {
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
			server.incomingMessage("ROBOT", ">>>>>>>>>>>>>>>>[" + name
					+ "] has entered the Room");
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
		server.incomingMessage("ROBOT", "<<<<<<<<<<<<<<<<[" + name
				+ "] has left the Room");
	}

	private void printPrompt() {
		out.print(getPrompt());
	}

	private String getPrompt() {
		return name + ":";
	}

	private void handleConnection() throws IOException {
		String input = null;
		while (reader.ready()) {
			input = reader.readLine();
		}
		if (input != null) {
			server.incomingMessage(name, input);
		}
	}
}