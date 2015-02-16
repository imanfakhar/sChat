package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HandlerRunnable implements Runnable {
	private static PrintWriter out;
	private Socket acceptedClient;
	private App app;
	private String name = null;

	public HandlerRunnable(Socket acceptedClient, App app) throws IOException {
		this.acceptedClient = acceptedClient;
		this.app = app;
		out = new PrintWriter(acceptedClient.getOutputStream(), true);

	}

	public synchronized void outgoingMessage(String input) {
		System.out.println("out hash of:[" + name + "]: " + out.toString());
		if (name != null) {
			out.println(input);
		}
	}

	public void run() {
		try {
			out.println("Bitte geben Sie ihren Nutzername an");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					acceptedClient.getInputStream()));
			this.name = reader.readLine();
			out.println("Vielen Dank, " + name + "!");
			System.out.println(this.toString() + " heiﬂt jetzt : [" + name
					+ "]");

			while (true) {
				reader = new BufferedReader(new InputStreamReader(
						acceptedClient.getInputStream()));
				handleConnection(acceptedClient);
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

	private void handleConnection(Socket client) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		String input = null;
		while (reader.ready()) {
			input = reader.readLine();
		}
		if (input != null) {
			this.app.incomingMessage(name, input);
		}
	}
}