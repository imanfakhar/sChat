package de.sChat.client.connector;

public class Connection {
	StreamReader streamReader;
	StreamWriter streamwriter;

	public StreamReader getStreamReader() {
		return streamReader;
	}

	public StreamWriter getStreamwriter() {
		return streamwriter;
	}

	public Connection(StreamReader streamReader,
			StreamWriter streamWriter) {
		this.streamReader = streamReader;
		this.streamwriter = streamwriter;
	}

}
