package de.sChat.client.connector;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connector {
	private String ipAddress;
	private String port;

	public Connector(String ipAddress, String port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public Connection connectWithName(String name)
			throws NumberFormatException, IOException {
		InetAddress serverAddress = InetAddress.getByName(ipAddress);
		Socket socket = new Socket(serverAddress, Integer.parseInt(port));
		StreamReaderRunnable streamReader = new StreamReaderRunnable(
				socket.getInputStream());
		Thread streamReaderThread = new Thread(streamReader);
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

		StreamWriterRunnable streamWriter = new StreamWriterRunnable(writer);
		Thread streamWriterThread = new Thread(streamWriter);

		streamReaderThread.start();
		streamWriterThread.start();

		return new Connection(streamReader, streamWriter);
	}
}
