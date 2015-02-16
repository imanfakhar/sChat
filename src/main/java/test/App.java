package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class App {
	private Vector<HandlerThread> listeMitThreads = new Vector<HandlerThread>();

	private App() throws IOException {
		ServerSocket server = new ServerSocket(4321);

		while (true) {
			Socket client = null;
			client = server.accept();
			HandlerThread newHandler = new HandlerThread(client, this);
			Thread thread = new Thread(newHandler);
			listeMitThreads.add(newHandler);
			thread.start();
		}
	}

	public static void main(String[] args) throws IOException {
		new App();
	}

	public synchronized void incomingMessage(String input) {
		for (HandlerThread handlerThread : listeMitThreads) {
			handlerThread.outgoingMessage(input);
		}
	}

}

class HandlerThread implements Runnable {
	private static PrintWriter out;
	private Socket acceptedClient;
	private App app;

	public HandlerThread(Socket acceptedClient, App app) {
		this.acceptedClient = acceptedClient;
		this.app = app;

	}

	public synchronized void outgoingMessage(String input) {
		out.println(input);
	}

	public void run() {
		try {
			while (true) {
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
		out = new PrintWriter(client.getOutputStream(), true);
		String input = null;
		while (reader.ready()) {
			input = reader.readLine();
		}
		if (input != null) {
			this.app.incomingMessage(input);
		}
	}
}