package de.sChat.client.connector;

import java.io.IOException;
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

		streamReader.start();

		return new Connection(socket);
	}
}
