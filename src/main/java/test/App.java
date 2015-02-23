package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class App {
	private Vector<HandlerRunnable> listeMitThreads = new Vector<HandlerRunnable>();

	private App() throws IOException {
		ServerSocket server = new ServerSocket(4321);

		while (!isCloseRequested()) {
			Socket client = server.accept();
			HandlerRunnable newHandler = new HandlerRunnable(client, this);
			Thread thread = new Thread(newHandler);
			listeMitThreads.add(newHandler);
			thread.start();
		}
		server.close();
	}

	private boolean isCloseRequested() {
		Thread actualThread = Thread.currentThread();
		if (actualThread.getState().equals(Thread.State.TERMINATED)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		new App();
	}

	public synchronized void incomingMessage(String name, String input) {
		for (HandlerRunnable handlerThread : listeMitThreads) {
			handlerThread.outgoingMessage(name, input);
		}
	}

}
