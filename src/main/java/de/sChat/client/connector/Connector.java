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
		InetAddress serverAddress = InetAddress.getByName(name);
		Socket socket = new Socket(serverAddress, new Integer(port).intValue());
		Thread streamReader = new Thread(new StreamReaderRunnable(
				socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		Thread streamWriter = new Thread(new StreamWriterRunnable(writer));

		streamReader.start();
		streamWriter.start();

		return new Connection(socket);
	}
}
