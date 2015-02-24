package de.sChat.client.connector;

public class Connection {
	StreamReader streamReader;
	StreamWriter streamWriter;

	public StreamReader getStreamReader() {
		return streamReader;
	}

	public StreamWriter getStreamwriter() {
		return streamWriter;
	}

	public Connection(StreamReader streamReader, StreamWriter streamWriter) {
		this.streamReader = streamReader;
		this.streamWriter = streamWriter;
	}

}
